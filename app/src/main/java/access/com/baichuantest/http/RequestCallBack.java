package access.com.baichuantest.http;

/**
 * Created by Administrator on 2017/6/26.
 */

public interface RequestCallBack<T> {

    void onResponse(T response);

    void onFailure(String code, Throwable t);
}
