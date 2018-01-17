package access.com.baichuantest.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import access.com.baichuantest.utils.SharedPreferencesUtil;

/**
 * Created by xufangqiang on 2017/11/23.
 */

public class SharedPreferenceData {

    public static void setLookHistoryGuideIsShow(boolean param) {
        SharedPreferencesUtil.putBoolean(Key.sharePreferenceName,
                Key.SHARE_PREFERENCE_GUIDE_LOOK_HISTORY, param);
    }

    public static boolean getLookHistoryGuideIsShow() {
        return SharedPreferencesUtil.getBoolean(Key.sharePreferenceName,
                Key.SHARE_PREFERENCE_GUIDE_LOOK_HISTORY);
    }

    public static void setTopicGuideIsShow(boolean param) {
        SharedPreferencesUtil.putBoolean(Key.sharePreferenceName,
                Key.SHARE_PREFERENCE_GUIDE_TOPIC, param);
    }

    public static boolean getTopicGuideIsShow() {
        return SharedPreferencesUtil.getBoolean(Key.sharePreferenceName,
                Key.SHARE_PREFERENCE_GUIDE_TOPIC);
    }

    public static void setGroupWxIndex(int index) {
        SharedPreferencesUtil.putInt(Key.sharePreferenceName,
                Key.SHARE_PREFERENCE_GROUP_WX_INDEXES, index);
    }

    // 第几轮微信发送
    public static int getGroupWxIndex() {
        return SharedPreferencesUtil.getInt(Key.sharePreferenceName,
                Key.SHARE_PREFERENCE_GROUP_WX_INDEXES, 0);
    }

    public static void setGroupWxIndexForTimes(int index) {
        SharedPreferencesUtil.putInt(Key.sharePreferenceName,
                Key.SHARE_PREFERENCE_GROUP_WX_INDEXES_TIMES, index);
    }

    // 发送到第几个群，微信所有的群一次发送
    public static int getGroupWxIndexForTimes() {
        return SharedPreferencesUtil.getInt(Key.sharePreferenceName,
                Key.SHARE_PREFERENCE_GROUP_WX_INDEXES_TIMES, 0);
    }

    public static void setGroupQQIndex(int index) {
        SharedPreferencesUtil.putInt(Key.sharePreferenceName,
                Key.SHARE_PREFERENCE_GROUP_QQ_INDEXES, index);
    }

    // 第几次QQ发送
    public static int getGroupQQIndex() {
        return SharedPreferencesUtil.getInt(Key.sharePreferenceName,
                Key.SHARE_PREFERENCE_GROUP_QQ_INDEXES, 0);
    }

}
