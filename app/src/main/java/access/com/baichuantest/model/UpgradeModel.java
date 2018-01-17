package access.com.baichuantest.model;

import java.io.Serializable;

/**
 * Created by wzg on 15/7/7.
 */
public class UpgradeModel implements Serializable {

    public UpgradeModel() {

    }

    private String version;//:'1.0.1',
    private String is_force;//:'Y',
    private String description;//:'升级描述',
    private String download_url;//:'http://ytfile.oss-cn-hangzhou.aliyuncs.com/youngt.apk',
    private String is_upgrade;//Y升级 N不升级

    public String getIs_upgrade() {
        return is_upgrade;
    }

    public void setIs_upgrade(String is_upgrade) {
        this.is_upgrade = is_upgrade;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIs_force() {
        return is_force;
    }

    public void setIs_force(String is_force) {
        this.is_force = is_force;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
}
