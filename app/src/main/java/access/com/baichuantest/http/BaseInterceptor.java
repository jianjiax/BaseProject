package access.com.baichuantest.http;

import java.io.IOException;

import access.com.baichuantest.BuildConfig;
import access.com.baichuantest.utils.SystemUtil;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BaseInterceptor implements Interceptor {

    private final String API_VERSION = "3";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        //添加请求参数
        HttpUrl url = original.url().newBuilder()
                .addQueryParameter("V", API_VERSION)
                .build();
        //添加请求头
        Request request = original.newBuilder()
//                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                .addHeader("Connection", "keep-alive")
//        taodianke/2.1.0 (OPPO/OPPO R11t; Android/7.1.1)
                .addHeader("User-Agent", "taodianke/" + BuildConfig.VERSION_NAME
                        +" (" + SystemUtil.getDeviceBrand() + "/" + SystemUtil.getSystemModel()
                        + ";Android" + SystemUtil.getSystemVersion() +")")
                .method(original.method(), original.body())
                .url(url)
                .build();
        return chain.proceed(request);
    }
}