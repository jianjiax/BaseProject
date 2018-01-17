package access.com.baichuantest.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;

import access.com.baichuantest.utils.EncryptUtils;
import access.com.baichuantest.utils.LogUtils;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// 原生callback
//new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//                LogUtils.e("response == " + response.toString());
//                LogUtils.e("response1 == " + response.body());
//                try {
//                    // gson.fromJson直接引用response.body().string()获取不到
//                    String str = response.body().string();
//                    LogUtils.e("stre == " + str);
//                    StoreDetails storeDetails = gson.fromJson(str, new TypeToken<StoreDetails>() {
//                    }.getType());
//                    LogUtils.e("storeDetails == " + storeDetails.getStore_info().getStore_name());
//                } catch (IOException e) {
//                    LogUtils.e("IOExceptionIOException");
//                    e.printStackTrace();
//                }
////                    storeDetails = response.body();
////                    setData();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        }

// Iterator iterator = paramMap.entrySet().iterator();
//         while (iterator.hasNext()) {
//         java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
//         LogUtils.e(entry.getKey() + "   " + entry.getValue());
//         }

/**
 * tips:如果token失效（code == -4），页面会被finish并跳转到login页面，所以当一个页面有多个请求的时候
 * 不建议并发请求，页面finish时如果有请求未完成可能导致crash --- 尚未验证
 * Created by Administrator on 2017/6/24.
 */
public class ApiRetrofitImpl {

    private final String JSON_KEY_CODE = "code";
    private final String JSON_KEY_MSG = "msg";
    private final String REQUEST_FAILED_MSG_DEFAULT = "网络异常";

    private final String BASE_URL = "";

    public static final String REQUEST_CODE_SUCCESS = "0";
    public static final String REQUEST_CODE_FAILED = "-1";

    private RequestCallBack callback;

    private ApiRequest apiRequest;

    private Gson gson;

    private Type type;

    private SmartRefreshLayout smartRefreshLayout;

    private RequestFinishListener finishListener;

    public interface RequestFinishListener {
        void requestFinished(String code);
    }

    public ApiRetrofitImpl() {
        gson = new Gson();

        // 添加公共参数
        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        client.connectTimeout(5, TimeUnit.SECONDS);
        client.addInterceptor(new BaseInterceptor());

        // base url must end in "/"
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiRequest = retrofit.create(ApiRequest.class);
    }

    public void setCallBack(RequestCallBack callback) {
        this.callback = callback;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setRefreshLayout(SmartRefreshLayout refreshLayout) {
        this.smartRefreshLayout = refreshLayout;
    }

    public void setFinishListener(RequestFinishListener listener) {
        this.finishListener = listener;
    }

    private void setRequestFinish(String codeStr) {
        if(null != smartRefreshLayout){
            smartRefreshLayout.finishRefresh(codeStr.equals(REQUEST_CODE_SUCCESS));
            smartRefreshLayout.finishLoadmore(codeStr.equals(REQUEST_CODE_SUCCESS));
        }
        if (null != finishListener) {
            finishListener.requestFinished(codeStr);
        }
    }

    public void startRequest(Call call) {
//        call.enqueue(callback);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                LogUtils.e("response == " + response.toString());
                String msg = REQUEST_FAILED_MSG_DEFAULT;
                String code;
                String result = "";
                try {
                    if (response.code() == 200) {
                        // gson.fromJson直接引用response.body().string()获取不到
                        result = response.body().string();
                        LogUtils.e("result == " + result);

                        JSONObject jsonObject = new JSONObject(result);
                        code = jsonObject.getString(JSON_KEY_CODE);
                        msg = jsonObject.getString(JSON_KEY_MSG);
                    } else {
                        code = REQUEST_CODE_FAILED;
                    }

                    setRequestFinish(code);
                    if (REQUEST_CODE_SUCCESS.equals(code)) {
                        callback.onResponse(type == null ? result : gson.fromJson(result, type));
                    } else {
                        callback.onFailure(code, new Throwable(msg));
                    }
                } catch (Exception e) {
                    // IOException,IllegalStateException,JSONException
                    LogUtils.e("request exception: " + e.getMessage());
                    e.printStackTrace();
                    setRequestFinish(REQUEST_CODE_FAILED);
                    callback.onFailure(REQUEST_CODE_FAILED, new Throwable(REQUEST_FAILED_MSG_DEFAULT));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtils.e("Throwable == " + t.toString());
                setRequestFinish(REQUEST_CODE_FAILED);
                callback.onFailure(REQUEST_CODE_FAILED, new Throwable(REQUEST_FAILED_MSG_DEFAULT));
            }
        });
    }

    private String getValueForJson(JSONObject jsonObject, String keyStr) {
        String result;
        try {
            result = jsonObject.getString(keyStr);
        } catch (JSONException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    public void login(String username, String password) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("username", username);
        paramMap.put("password", password);
        startRequest(apiRequest.login(username, password));
    }

    public void register(String key, String mobile, String code, String password, String compass, String referee) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("key", key);
        paramMap.put("mobile", mobile);
        paramMap.put("code", code);
        paramMap.put("password", password);
        paramMap.put("compass", compass);
        paramMap.put("referee", referee);
        startRequest(apiRequest.register(paramMap));
    }

    /**
     * @param token
     * @param keyword
     * @param condition
     * @param hasQw     是否包含全网商品
     * @param page
     */
    public void search(String token, String keyword, String condition, boolean hasQw, String page) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("keyword", keyword);
        paramMap.put("page", page);
        paramMap.put("condition", condition);
        paramMap.put("is_full_web_product", hasQw ? "Y" : "N");

        Iterator iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
            LogUtils.e(entry.getKey() + "   " + entry.getValue());
        }
        startRequest(apiRequest.search(paramMap));
    }

    /**
     * advert
     */
    public void advert(String token) {
        HashMap<String, String> paramMap = new HashMap<>();
        if(!TextUtils.isEmpty(token)){
            paramMap.put("token", token);
        }
        startRequest(apiRequest.advert(paramMap));
    }

}
