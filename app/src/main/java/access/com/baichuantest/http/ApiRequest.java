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

    @GET("Index/cate")
    Call<ResponseBody> category(@QueryMap Map<String, String> params);

    @GET("Index/index")
    Call<ResponseBody> indexGoods(@QueryMap Map<String, String> params);

    @GET("Item/detail")
    Call<ResponseBody> goodsDetails(@QueryMap Map<String, String> params);

    @GET("Item/index")
    Call<ResponseBody> goodsList(@QueryMap Map<String, String> params);

    @GET("Balance/index")
    Call<ResponseBody> chartIndex(@Query("token") String token);

    @GET("Balance/getOrder")
    Call<ResponseBody> order(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("User/index")
    Call<ResponseBody> userInfo(@Field("token") String token);

    @GET("User/addLike")
    Call<ResponseBody> addLike(@Query("token") String token, @Query("num_iid") String num_iid);

    @GET("User/delLike")
    Call<ResponseBody> delLike(@Query("token") String token, @Query("num_iid") String num_iid);

    @GET("User/like")
    Call<ResponseBody> collect(@Query("token") String token, @Query("page") String page);

    @GET("User/cash")
    Call<ResponseBody> applySettle(@Query("token") String token);

    @GET("User/cashList")
    Call<ResponseBody> settleList(@Query("token") String token, @Query("page") String page);

    @FormUrlEncoded
    @POST("User/addBank")
    Call<ResponseBody> bindAccount(@FieldMap Map<String, String> params);

    @GET("Public/checkUpdate")
    Call<ResponseBody> update(@Query("ver") String ver, @Query("plat") String plat);

    @GET("Public/appLoadInit")
    Call<ResponseBody> loadInit();

    @FormUrlEncoded
    @POST("Public/sendCode")
    Call<ResponseBody> sendCode(@FieldMap Map<String, String> params);

    @GET("Item/highApply")
    Call<ResponseBody> applyAmount(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("User/updateInfo")
    Call<ResponseBody> updateUserInfo(@FieldMap Map<String, String> params);

    @GET("Proxy/subProxy")
    Call<ResponseBody> subProxy(@Query("token") String token, @Query("page") String page);

    @GET("Proxy/subProxyOrderCommission")
    Call<ResponseBody> proxyOrder(@QueryMap Map<String, String> params);

    @GET("Balance/search")
    Call<ResponseBody> incomeRecord(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Suggest/add")
    Call<ResponseBody> feedBack(@Field("token") String token, @Field("info") String info);

    @GET("Notice/index")
    Call<ResponseBody> newsList(@QueryMap Map<String, String> params);

    @GET("Item/special")
    Call<ResponseBody> getGoodsForType(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Proxy/subProxyRatio")
    Call<ResponseBody> setRatio(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("User/updatePassword")
    Call<ResponseBody> modifyPassword(@FieldMap Map<String, String> params);

    @GET("Item/similar")
    Call<ResponseBody> getGoodsForSimilar(@QueryMap Map<String, String> params);

    @GET("Proxy/getWxQrcode")
    Call<ResponseBody> getPoster(@QueryMap Map<String, String> params);

    @GET("Index/getAppModule")
    Call<ResponseBody> getHomeMenu(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Item/oneKeyTurnChain")
    Call<ResponseBody> turnChain(@FieldMap Map<String, String> params);

    @GET("WeChatRobot/getQRString")
    Call<ResponseBody> getQrStrForWx(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("WeChatRobot/mass")
    Call<ResponseBody> groupSendForWx(@FieldMap Map<String, String> params);

    @GET("WeChatRobot/checkLogin")
    Call<ResponseBody> queryLogin(@QueryMap Map<String, String> params);

    @GET("Coupon/detail")
    Call<ResponseBody> couponDetail(@QueryMap Map<String, String> params);

    @GET("Coupon/useCoupon")
    Call<ResponseBody> useCoupon(@QueryMap Map<String, String> params);

    @GET("Coupon/index")
    Call<ResponseBody> couponHistory(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Cashier/add")
    Call<ResponseBody> addCashier(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Cashier/edit")
    Call<ResponseBody> editCashier(@FieldMap Map<String, String> params);

    @GET("Cashier/index")
    Call<ResponseBody> cashierList(@QueryMap Map<String, String> params);

    @GET("Proxy/customerList")
    Call<ResponseBody> posterRecord(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Topic/add")
    Call<ResponseBody> addTopic(@FieldMap Map<String, String> params);

    @GET("Topic/index")
    Call<ResponseBody> topicList(@QueryMap Map<String, String> params);


}
