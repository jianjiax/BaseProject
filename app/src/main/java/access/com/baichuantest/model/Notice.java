package access.com.baichuantest.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 首页弹窗通知
 * Created by xufangqiang on 2017/10/10.
 */

public class Notice implements Serializable {

    private String id;
    private String title;
    private String info;
    private String add_time;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public long getAdd_time() {
        return TextUtils.isEmpty(this.add_time) ? 0 : Long.parseLong(this.add_time);
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
