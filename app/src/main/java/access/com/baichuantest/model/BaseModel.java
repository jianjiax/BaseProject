package access.com.baichuantest.model;

import java.io.Serializable;

/**
 * Created by wzg on 15/4/20.
 * 所有对象的基类
 */
public class BaseModel<T> implements Serializable{
    private String code;
    private String msg;
    private String hasnext;
    private T data;

    public BaseModel() {

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getHasnext() {
        return hasnext;
    }

    public void setHasnext(String hasnext) {
        this.hasnext = hasnext;
    }

}
