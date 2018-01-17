package access.com.baichuantest.http;


import access.com.baichuantest.model.BaseModel;

/**
 * Created by Kain on 2016/8/30.
 */
public interface CallBack<T> {
    /**
     * 请求完成后回调该方法
     *
     * @param isSuccess 请求是否成功
     * @param response  如果请求成功该参数不为空否则为空
     * @param error     如果请求成功该参数为空否则会将失败原因传入
     */
    void onFinish(boolean isSuccess, BaseModel<T> response, String error);
}