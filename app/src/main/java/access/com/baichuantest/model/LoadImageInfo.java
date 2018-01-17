package access.com.baichuantest.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/5/18.
 */
public class LoadImageInfo implements Serializable {

    private String url;
    private String is_use;
    private String md5_file;
    private String topic_url;
    private String action_url;
    private Notice new_notice;
    private long look_time;

    public String getAction_url() {
        return action_url;
    }

    public void setAction_url(String action_url) {
        this.action_url = action_url;
    }

    public long getLook_time() {
        return look_time;
    }

    public void setLook_time(long look_time) {
        this.look_time = look_time;
    }

    public String getTopic_url() {
        return topic_url;
    }

    public void setTopic_url(String topic_url) {
        this.topic_url = topic_url;
    }

    public Notice getNew_notice() {
        return new_notice;
    }

    public void setNew_notice(Notice new_notice) {
        this.new_notice = new_notice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIs_use() {
        return is_use;
    }

    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    public String getMd5_file() {
        return md5_file;
    }

    public void setMd5_file(String md5_file) {
        this.md5_file = md5_file;
    }
}
