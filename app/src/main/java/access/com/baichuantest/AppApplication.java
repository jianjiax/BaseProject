package access.com.baichuantest;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * Created by Kain on 2016/9/22.
 */
public class AppApplication extends Application {

    private static AppApplication instance;

    public static AppApplication getInstance() {
        return instance;
    }

    public boolean hasPatch = false;

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
//        SharedPreferencesUtil.init(getApplicationContext());
//
//        if(!BuildConfig.DEBUG){
//            // initialize最好放在attachBaseContext最前面
//            SophixManager.getInstance().setContext(this)
//                    .setAppVersion(BuildConfig.VERSION_NAME)
//                    .setAesKey(null)
//                    .setEnableDebug(BuildConfig.DEBUG)
//                    .setPatchLoadStatusStub(new PatchLoadStatusListener() {
//                        @Override
//                        public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
//                            Log.e("sophix","code == " + code);
//                            Log.e("sophix","info == " + info);
//                            // 补丁加载回调通知
//                            if (code == PatchStatus.CODE_LOAD_SUCCESS) {
//                                // 表明补丁加载成功
//                                setHasPatch(false);
//                            } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
//                                // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
//                                // 建议: 用户可以监听进入后台事件, 然后应用自杀
////                            SophixManager.getInstance().killProcessSafely();
//
//                                setHasPatch(true);
//                            } else if (code == PatchStatus.CODE_LOAD_FAIL) {
//                                // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
//                                // SophixManager.getInstance().cleanPatches();
//                            } else {
//                                // 其它错误信息, 查看PatchStatus类说明
//                            }
//                        }
//                    }).initialize();
//            SophixManager.getInstance().queryAndLoadNewPatch();
//        }

        init();
    }

    public boolean isHasPatch() {
        return hasPatch;
    }

    public void setHasPatch(boolean hasPatch) {
        this.hasPatch = hasPatch;
    }

    private void init() {
        instance = this;
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.wholeColor, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });

        initBugTags();

////        MobclickAgent.setDebugMode(true);
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
//
////        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
//
//        SharedPreferencesUtil.init(this);
    }

    private void initBugTags() {
//        BugtagsOptions options = new BugtagsOptions.Builder().
//                trackingLocation(false).//是否获取位置，默认 true
//                trackingCrashLog(true).//是否收集crash，默认 true
//                trackingConsoleLog(true).//是否收集console log，默认 true
//                trackingUserSteps(true).//是否收集用户操作步骤，默认 true
//                trackingNetworkURLFilter("(.*)").//自定义网络请求跟踪的 url 规则，默认 null
//                build();
////        release
//        Bugtags.start("57930c0d9ee289448dc83750b8bd05d5", this, Bugtags.BTGInvocationEventNone, options);
    }

}
