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

    public void category(String token) {
        HashMap<String, String> paramMap = new HashMap<>();
        if(!TextUtils.isEmpty(token)){
            paramMap.put("token", token);
        }
        startRequest(apiRequest.category(paramMap));
    }

    public void indexGoods(String token, String page) {
        HashMap<String, String> paramMap = new HashMap<>();
        if(!TextUtils.isEmpty(token)){
            paramMap.put("token", token);
        }
        paramMap.put("page", page);
        startRequest(apiRequest.indexGoods(paramMap));
    }

    public void goodsDetails(String token, String id, String type,String zone_id) {
        HashMap<String, String> paramMap = new HashMap<>();
        if(!TextUtils.isEmpty(token)){
            paramMap.put("token", token);
        }
        paramMap.put("id", id);
        paramMap.put("type", type);
        paramMap.put("zone_id", zone_id);
        startRequest(apiRequest.goodsDetails(paramMap));
    }

    public void goodsList(String token, String cate_id, String page, String condition) {
        HashMap<String, String> paramMap = new HashMap<>();
        if(!TextUtils.isEmpty(token)){
            paramMap.put("token", token);
        }
        paramMap.put("cate_id", cate_id);
        paramMap.put("page", page);
        paramMap.put("condition", condition);

        Iterator iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
            LogUtils.e(entry.getKey() + "   " + entry.getValue());
        }
        startRequest(apiRequest.goodsList(paramMap));
    }

//    /**
//     * article index
//     */
//    public void articleIndex() {
//        HashMap<String, String> paramMap = new HashMap<>();
//        request(HttpMethod.GET, ApiUrlCentre.ARTICLEINDEX, paramMap);
//    }
//
//    /**
//     * article list
//     *
//     * @param type_id category id
//     */
//    public void articleList(String type_id, String last_id) {
//        HashMap<String, String> paramMap = new HashMap<>();
//        paramMap.put("type_id", type_id);
//        paramMap.put("last_id", last_id);
//        request(HttpMethod.GET, ApiUrlCentre.ARTICLELIST, paramMap);
//    }
//
//    /**
//     * article details
//     *
//     * @param id article id
//     */
//    public void articleDetails(String id) {
//        HashMap<String, String> paramMap = new HashMap<>();
//        paramMap.put("id", id);
//        request(HttpMethod.GET, ApiUrlCentre.ARTICLEDETAILS, paramMap);
//    }

    public void chartIndex(String token) {
        startRequest(apiRequest.chartIndex(token));
    }

    public void order(String token, String payStatus, String add_start_day, String add_end_day, String page) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("payStatus", payStatus);
        if(!TextUtils.isEmpty(add_start_day)){
            paramMap.put("add_start_day", add_start_day);
        }
        if(!TextUtils.isEmpty(add_end_day)){
            paramMap.put("add_end_day", add_end_day);
        }
        paramMap.put("page", page);
        startRequest(apiRequest.order(paramMap));
    }

    public void userInfo(String token) {
        startRequest(apiRequest.userInfo(token));
    }

    public void addLike(String token, String num_iid) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("num_iid", num_iid);
        startRequest(apiRequest.addLike(token, num_iid));
    }

    public void delLike(String token, String num_iid) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("num_iid", num_iid);
        startRequest(apiRequest.delLike(token, num_iid));
    }

    public void collect(String token, String page) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("page", page);
        startRequest(apiRequest.collect(token, page));
    }

    /**
     * 申请结算
     *
     * @param token
     */
    public void applySettle(String token) {
        startRequest(apiRequest.applySettle(token));
    }

    public void settleList(String token, String page) {
        startRequest(apiRequest.settleList(token, page));
    }

    public void bindAccount(String token, String bank_name, String bank_account, String real_name) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("bank_account", bank_account);
//        try {
//            paramMap.put("bank_name", URLEncoder.encode(bank_name,"utf-8"));
//            paramMap.put("real_name", URLEncoder.encode(real_name,"utf-8"));
        paramMap.put("bank_name", bank_name);
        paramMap.put("real_name", real_name);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        startRequest(apiRequest.bindAccount(paramMap));
    }

    public void update(String ver) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("ver", ver);
        paramMap.put("plat", "android");
        startRequest(apiRequest.update(ver, "android"));
    }

    public void loadInit() {
        startRequest(apiRequest.loadInit());
    }

    public void sendCode(String key, String mobile) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("mobile", mobile);
        paramMap.put("key", key);
        startRequest(apiRequest.sendCode(paramMap));
    }

    /**
     * 申请高佣
     *
     * @param goodid
     */
    public void applyAmount(String token, String goodid, String rate) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("id", goodid);
        paramMap.put("token", token);
        paramMap.put("rate", rate);
        startRequest(apiRequest.applyAmount(paramMap));
    }

    public void updateUserInfo(String token, String username) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("username", username);
        paramMap.put("token", token);
        startRequest(apiRequest.updateUserInfo(paramMap));
    }

    public void subProxy(String token, String page) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("page", page);
        startRequest(apiRequest.subProxy(token, page));
    }

    /**
     * 获取子代理订单或者已结算的订单
     *
     * @param token
     * @param proxy_pid
     * @param page
     */
    public void proxyOrder(String token, String proxy_pid, String page) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("proxy_pid", proxy_pid);
        paramMap.put("token", token);
        paramMap.put("page", page);
        startRequest(apiRequest.proxyOrder(paramMap));
    }

    public void incomeRecord(String token, String start_day, String end_day,String zone_id) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("start_day", start_day);
        paramMap.put("end_day", end_day);
        paramMap.put("zone_id", zone_id);
        startRequest(apiRequest.incomeRecord(paramMap));
    }

    public void feedBack(String token, String info) {
        startRequest(apiRequest.feedBack(token, info));
    }

    public void newsList(String token) {
        HashMap<String, String> paramMap = new HashMap<>();
        if(!TextUtils.isEmpty(token)){
            paramMap.put("token", token);
        }
        startRequest(apiRequest.newsList(paramMap));
    }

    /**
     * 获取结算的订单
     *
     * @param token
     * @param withdraw_id 结算的id
     * @param page
     */
    public void settleOrder(String token, String withdraw_id, String page) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("withdraw_id", withdraw_id);
        paramMap.put("page", page);
        startRequest(apiRequest.order(paramMap));
    }

    public void getGoodsForType(String token, String type, String page) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("type", type);
        paramMap.put("page", page);
        startRequest(apiRequest.getGoodsForType(paramMap));
    }

    /**
     * 设置分成比例
     *
     * @param token
     * @param proxy_user_id
     * @param ratio         1-100
     */
    public void setRatio(String token, String proxy_user_id, String ratio) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("proxy_user_id", proxy_user_id);
        paramMap.put("ratio", ratio);
        startRequest(apiRequest.setRatio(paramMap));
    }

    public void modifyPassword(String token, String oldPw, String newPw) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("old_password", EncryptUtils.getMessageDigest(oldPw, ""));
        paramMap.put("new_password", EncryptUtils.getMessageDigest(newPw, ""));
        startRequest(apiRequest.modifyPassword(paramMap));
    }

    public void getGoodsForSimilar(String token, String num_iid) {
        HashMap<String, String> paramMap = new HashMap<>();
        if(!TextUtils.isEmpty(token)){
            paramMap.put("token", token);
        }
        paramMap.put("num_iid", num_iid);
        startRequest(apiRequest.getGoodsForSimilar(paramMap));
    }

    public void getPoster(String token) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        startRequest(apiRequest.getPoster(paramMap));
    }

    public void getHomeMenu(String token) {
        HashMap<String, String> paramMap = new HashMap<>();
        if(!TextUtils.isEmpty(token)){
            paramMap.put("token", token);
        }
        paramMap.put("client", "android");
        startRequest(apiRequest.getHomeMenu(paramMap));
    }

    public void turnChain(String token,String item_info) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("item_info", item_info);
        startRequest(apiRequest.turnChain(paramMap));
    }

    public void getQrStrForWx(String token) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        startRequest(apiRequest.getQrStrForWx(paramMap));
    }

    public void groupSendForWx(String token, String type, String text, String pic_url, String send_name) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("type", type);
        paramMap.put("text", text);
        if(!TextUtils.isEmpty(pic_url)){
            paramMap.put("pic_url", pic_url);
        }
        paramMap.put("send_name", send_name);

        Iterator iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
            LogUtils.e(entry.getKey() + "   " + entry.getValue());
        }
        startRequest(apiRequest.groupSendForWx(paramMap));
    }

    public void queryLoginForWxApi(String token) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        startRequest(apiRequest.queryLogin(paramMap));
    }

    public void couponDetails(String token,String code) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("coupon_sn", code);
        startRequest(apiRequest.couponDetail(paramMap));
    }

    public void useCoupon(String token,String code) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("coupon_sn", code);
        startRequest(apiRequest.useCoupon(paramMap));
    }

    public void couponHistory(String token,String cashierId,String startDay,String endDay) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("cashier_id", cashierId);
        paramMap.put("start_day", startDay);
        paramMap.put("end_day", endDay);
        startRequest(apiRequest.couponHistory(paramMap));
    }

    public void addCashier(String token,String name,String phone) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("real_name", name);
        paramMap.put("cashier_mobile", phone);
        startRequest(apiRequest.addCashier(paramMap));
    }

    public void editCashier(String token,String cashierId,String status) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("cashier_id", cashierId);
        paramMap.put("status", status);
        startRequest(apiRequest.editCashier(paramMap));
    }

    public void cashierList(String token,String page) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("page", page);
        startRequest(apiRequest.cashierList(paramMap));
    }

    public void posterRecord(String token,String start_day,String end_day,String page) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("start_day", start_day);
        paramMap.put("end_day", end_day);
        paramMap.put("page", page);
        startRequest(apiRequest.posterRecord(paramMap));
    }

    public void addTopic(String token,String title,String desc,String ids) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("title", title);
        paramMap.put("desc", desc);
        paramMap.put("ids", ids);
        startRequest(apiRequest.addTopic(paramMap));
    }

    public void topicList(String token,String page) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", token);
        paramMap.put("page", page);
        startRequest(apiRequest.topicList(paramMap));
    }

}
