package access.com.baichuantest.http;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by xufangqiang on 2017/8/2.
 */

public interface ApiRequest {

    @FormUrlEncoded
    @POST("Public/login")
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("Public/registerForget")
    Call<ResponseBody> register(@FieldMap Map<String, String> params);

    //    @GET("Item/search")
//    Call<ResponseBody> search(@QueryMap Map<String, String> params);
    @FormUrlEncoded
    @POST("Item/search")
    Call<ResponseBody> search(@FieldMap Map<String, String> params);

    @GET("Index/getAdvert")
    Call<ResponseBody> advert(@QueryMap Map<String, String> params);

}
