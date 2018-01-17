package access.com.baichuantest.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.lang.reflect.Type;

import access.com.baichuantest.R;
import access.com.baichuantest.http.ApiRetrofitImpl;
import access.com.baichuantest.http.RequestCallBack;


/**
 * Created by xufangqiang on 2017/1/1.
 */

public class BaseActivity extends AppCompatActivity implements ApiRetrofitImpl.RequestFinishListener {

    // true for has next
    public static final String HAS_NEXT = "1";

    private final int REQUEST_FILE_PERMISSION_CODE = 11;

//    private ProgressDialog dialog;

    private ProgressDialog progressDialog;

    private static Toast toast;

    private Toolbar toolbar;

    private ApiRetrofitImpl apiRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public void setContentView(int layoutResID) {
//        ToolBarHelper mToolBarHelper = new ToolBarHelper(this, layoutResID);
//        toolbar = mToolBarHelper.getToolBar();
//        setContentView(mToolBarHelper.getContentView()); /*把 toolbar 设置到Activity 中*/
//
//        init();
//
////        LogUtils.e("getDevice info == " + OtherUtils.getDeviceInfo(this));
//    }
//
//    private void init() {
//        setSupportActionBar(toolbar); /*自定义的一些操作*/
//        onCreateCustomToolBar(toolbar, true, false);
//        setToolbarTitle(toolbar, "");
//        setToolbarBackText(toolbar, "");
//    }

//    /**
//     * @param toolbar
//     * @param isShowLeftMenu  是否显示左菜单
//     * @param isShowRightMenu 是否显右菜单
//     */
//    public void onCreateCustomToolBar(Toolbar toolbar, boolean isShowLeftMenu, boolean isShowRightMenu) {
//        toolbar.setContentInsetsRelative(0, 0);
//
////        ButtonIcon backBti = (ButtonIcon) toolbar.findViewById(R.id.back_bti);
//        ImageView back_iv = (ImageView) toolbar.findViewById(R.id.back_iv);
////        backBti.setRippleColor(getResources().getColor(R.color.red));
////        backBti.setBackgroundColor(getResources().getColor(R.color.tededed));
//        RelativeLayout rightRl = (RelativeLayout) toolbar.findViewById(R.id.toolbar_right_menu_rl);
//        // invisible,title center
//        back_iv.setVisibility(isShowLeftMenu ? View.VISIBLE : View.INVISIBLE);
//        rightRl.setVisibility(isShowRightMenu ? View.VISIBLE : View.INVISIBLE);
//        back_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Activity a = (Activity) (v.getContext());
//                a.finish();
//            }
//        });
//    }
//
//    public void setRightMenuVisibility(Toolbar toolbar, int paramInt) {
//        toolbar.findViewById(R.id.toolbar_right_menu_rl).setVisibility(paramInt);
//    }

    public void setToolbarTitle(String text) {
        TextView toolbar_title_tv = (TextView) findViewById(R.id.title_tv);
        toolbar_title_tv.setText(text);
    }

//    public void setToolbarBackText(Toolbar toolbar, String text) {
//        TextView back_text_tv = (TextView) toolbar.findViewById(R.id.back_text_tv);
//        back_text_tv.setText(text);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // return button
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    protected void openActivityIntent(Class<?> pClass, Intent intent) {
        intent.setClass(this, pClass);
        startActivity(intent);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    public void showProgressDialog() {
        showProgressDialog(true);
    }

    public void showProgressDialog(boolean allowCancel) {

//        dialog.setCanceledOnTouchOutside(allowCancel);
//        dialog.show();
        showProgressDialog(getString(R.string.loading), true);
    }

    public void showProgressDialog(String message, boolean allowCancel) {
        if (!this.isFinishing()) {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(this, "", message);
                progressDialog.setCanceledOnTouchOutside(allowCancel);
            } else {
                try {
                    progressDialog.show();
                } catch (Exception e) {

                }
            }
        }
    }

    public void closeProgressDialog() {
        if (progressDialog != null) {
            try {
                progressDialog.dismiss();
                progressDialog.cancel();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (WindowManager.BadTokenException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean hasPb = true;

    public ApiRetrofitImpl getApiRetrofit(RequestCallBack callback, Type type) {
        return getApiRetrofit(callback, type, null, true);
    }

    // get request
    public ApiRetrofitImpl getApiRetrofit(RequestCallBack callback, Type type, SmartRefreshLayout refreshLayout,boolean hasPb) {
        // 原本只想存在一个请求对象，但是并发请求的时候，请求类的set方法会设置新的对象，导致上一个请求不能正常完成而crash
//        if (null == apiRetrofit) {
//        }

        apiRetrofit = new ApiRetrofitImpl();
        this.hasPb = false;
        apiRetrofit.setCallBack(callback);
        apiRetrofit.setType(type);
        apiRetrofit.setRefreshLayout(refreshLayout);
        apiRetrofit.setFinishListener(this);
        if (hasPb) {
            showProgressDialog();
        }
        return apiRetrofit;
    }

    @Override
    public void requestFinished(String code) {
        switch (code) {
            case "1":
                break;
            case "2":
                break;
            default:
                break;
        }
        if (hasPb) {
            closeProgressDialog();
        }
    }

    /**
     * 显示toast
     */
    public void showToastShort(String msg) {
        // context = this时，retrofit的监听方法里toast会内存泄漏
        showToast(this.getApplicationContext(), msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast
     */
    public void showToastLong(String msg) {
        showToast(this, msg, Toast.LENGTH_LONG);
    }

    public static void showToast(Context contex, String msg, int time) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(contex, msg, time);
        toast.show();
    }

    /**
     * 隐藏输入法面板
     */
    public void hideKeyboard() {
        InputMethodManager imm = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

//    public String getToken() {
//        String token = SharedPreferencesUtil.getPreferences(AppApplication.getInstance().getApplicationContext(),
//                Key.sharePreferenceTokenName, Key.sharePreferenceTokenData);
//        return token == null ? "" : token;
//    }
//
//    public void setToken(String token) {
//        SharedPreferencesUtil.putPreferences(AppApplication.getInstance().getApplicationContext(), Key.sharePreferenceTokenName,
//                Key.sharePreferenceTokenData, token);
//    }
//
//    /**
//     * 缓存欢迎页数据
//     *
//     * @param imageInfo 需要重置的用户数据
//     */
//    public void setImageInfo(LoadImageInfo imageInfo) {
//        SharedPreferencesUtil.putPreferences(AppApplication.getInstance().getApplicationContext(), Key.sharePreferenceName,
//                Key.sharePreferenceImageInfo, imageInfo);
//    }
//
//    public LoadImageInfo getImageInfo() {
//        return SharedPreferencesUtil.getPreferences(AppApplication.getInstance().getApplicationContext(),
//                Key.sharePreferenceName, Key.sharePreferenceImageInfo);
//    }
//
//    public void setJPushAlias(String alias) {
//        String exitAlias = SharedPreferencesUtil.getPreferences(AppApplication.getInstance().getApplicationContext(),
//                Key.sharePreferenceName, Key.SHAREDPREFERENCES_SET_ALIAS);
//        if (TextUtils.isEmpty(alias)) {
//            return;
//        }
//        if (TextUtils.isEmpty(exitAlias) || !alias.equals(exitAlias)) {
//            setAlias(alias);
//        }
//    }
//
//    private void clearAlias() {
//        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
//        tagAliasBean.action = ACTION_DELETE;
//        tagAliasBean.isAliasAction = true;
//
//        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence + 1, tagAliasBean);
//    }
//
//    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
//    private void setAlias(String alias) {
//        if (TextUtils.isEmpty(alias)) {
//            return;
//        }
//        if (!StringUtils.isValidString(alias)) {
//            return;
//        }
////        clearAlias();
//        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
//        tagAliasBean.action = ACTION_SET;
//        tagAliasBean.alias = alias;
//        tagAliasBean.isAliasAction = true;
//
//        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, tagAliasBean);
//    }

    public boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    //    Manifest.permission.READ_CONTACTS
    public void getPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

//                showTipsDialog("permission", "not success");

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{permission},
                        requestCode);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

//    public void showShare(String title, String content,
//                          String imageUrl, String weChatUrl,
//                          String platform) {
//        showShare(title, content, imageUrl, weChatUrl, platform, false, false);
//    }
//
//    private boolean shareIsFinish = true;
//
//    /**
//     * @param title
//     * @param content
//     * @param imageUrl
//     * @param weChatUrl
//     * @param platformStr
//     * @param bypassApproval 是否绕过审核
//     */
//    public void showShare(String title, String content,
//                          String imageUrl, String weChatUrl,
//                          String platformStr, boolean isLocalImage, boolean bypassApproval) {
//
//        if (isLocalImage && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_FILE_PERMISSION_CODE);
//            showToastShort(getString(R.string.getFilePermissionTip));
//            return;
//        }
//        if (!shareIsFinish) {
//            return;
//        }
//        shareIsFinish = false;
//        if ((null == progressDialog || !progressDialog.isShowing()) && !TextUtils.isEmpty(platformStr)) {
//            showProgressDialog("正在启动分享...", true);
//        }
//        LogUtils.e("weChatUrl == " + weChatUrl);
//        LogUtils.e("imageUrl == " + imageUrl + " && isLocal == " + isLocalImage);
//        MobSDK.init(this, "1aad42726f6f8", "7d4ab539f3e01d5d01d6d566dde086dd");
//
//        // 分享到朋友圈，绕过审核不能分享为链接形式，不绕过审核不能分享图文(只有图片文字需要手动)
//        // 所以这里动态设置分享到朋友圈是否绕过审核，一键分享时默认设置为不绕过审核
//        // 第一次分享商品时绕过审核失败，第二次之后生效
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("Id", Wechat.NAME.equals(platformStr) ? "1" : "2");
//        map.put("SortId", Wechat.NAME.equals(platformStr) ? "1" : "2");
//        map.put("AppKey", Constants.WX_APP_ID);
//        map.put("AppKSecret", Constants.WX_APP_SECRET);
//        map.put("BypassApproval", bypassApproval && WechatMoments.NAME.equals(platformStr) ? "true" : "false");
//        map.put("enable", "true");
//        ShareSDK.setPlatformDevInfo(WechatMoments.NAME, map);
//
////        ShareSDK.initSDK(this);
//        OnekeyShare oks = new OnekeyShare();
//        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
//        if (platformStr != null) {
//            oks.setPlatform(platformStr);
//        }
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//        oks.setCallback(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                shareIsFinish = true;
//                closeProgressDialog();
//                LogUtils.e("onCompleteonComplete");
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                shareIsFinish = true;
//                closeProgressDialog();
//                LogUtils.e("onErroronError == " + throwable);
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                shareIsFinish = true;
//                closeProgressDialog();
//                LogUtils.e("onCancelonCancel");
//            }
//        });
//
//        if (Wechat.NAME.equals(platformStr) && isLocalImage) {
//
//            // 其他分享方式注释在末尾
//            // shareSDK在分享到微信时，分享本地图片，只能分享一次，所以删除其他参数。
//            oks.setImagePath(imageUrl);//确保SDcard下面存在此张图片
//            oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
//                @Override
//                public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
//                    paramsToShare.setShareType(Platform.SHARE_IMAGE);
//                }
//            });
//            // 启动分享GUI
//            oks.show(this);
//            return;
//        }
//
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle(title);
//        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl(weChatUrl);
//        // text是分享文本，所有平台都需要这个字段
//        if (!Wechat.NAME.equals(platformStr)) {
//            // 分享到微信设置text图片会变成文件
//            oks.setText(content);
//        }
//
//        if (isLocalImage) {
//            oks.setImagePath(imageUrl);//确保SDcard下面存在此张图片
//        } else {
//            //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//            oks.setImageUrl(imageUrl);
//        }
//
//        // 分享到微博设置链接或导致图片无法分享，朋友圈设置链接会导致分享的是链接不是图文
//        if (!SinaWeibo.NAME.equals(platformStr) && !WechatMoments.NAME.equals(platformStr)) {
//            oks.setUrl(weChatUrl);
//        }
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl(weChatUrl);
//        // 启动分享GUI
//        oks.show(this);
//    }

}