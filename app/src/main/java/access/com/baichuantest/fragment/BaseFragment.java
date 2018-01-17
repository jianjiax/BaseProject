package access.com.baichuantest.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;


import java.util.ArrayList;

import access.com.baichuantest.activity.BaseActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * Created by Kain on 2016/9/24.
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;

    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取布局文件ID
    protected abstract int getLayoutId();

    // true for has next
    public static final String HAS_NEXT = "1";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        return view;
    }

//    public String getToken() {
//        String token = SharedPreferencesUtil.getPreferences(AppApplication.getInstance().getApplicationContext(),
//                Key.sharePreferenceTokenName, Key.sharePreferenceTokenData);
//        return token == null ? "" : token;
//    }
//
//    public void clearToken() {
//        SharedPreferencesUtil.putPreferences(AppApplication.getInstance().getApplicationContext(),
//                Key.sharePreferenceTokenName, Key.sharePreferenceTokenData, null);
//    }
//
//    public UserInfo getUserInfo() {
//        return SharedPreferencesUtil.getPreferences(AppApplication.getInstance().getApplicationContext(),
//                Key.sharePreferenceName, Key.sharePreferenceUserInfo);
//    }

    /**
     * 隐藏输入法面板
     */
    public void hideKeyboard() {
        InputMethodManager imm = ((InputMethodManager) mActivity.getSystemService(INPUT_METHOD_SERVICE));
        if (imm != null && mActivity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏输入法面板
     */
    public void hideKeyboard(View view) {
        InputMethodManager imm = ((InputMethodManager) mActivity.getSystemService(INPUT_METHOD_SERVICE));
        if (imm != null && mActivity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //    Manifest.permission.READ_CONTACTS
    public void getPermission(String permission, int code) {
        if (ContextCompat.checkSelfPermission(mActivity,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {

                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION);
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
//                showTipsDialog("permission", "not success");

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(mActivity,
                        new String[]{permission}, code);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}