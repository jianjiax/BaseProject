package access.com.baichuantest.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import access.com.baichuantest.BuildConfig;
import access.com.baichuantest.R;
import access.com.baichuantest.adapter.BottomMenuAdapter;
import access.com.baichuantest.adapter.FragmentAdapter;
import access.com.baichuantest.fragment.fragmentnavigator.FragmentNavigator;
import access.com.baichuantest.http.RequestCallBack;
import access.com.baichuantest.model.BaseModel;
import access.com.baichuantest.model.UpgradeModel;
import access.com.baichuantest.utils.LogUtils;

/**
 * Created by xufangqiang on 2018/1/17.
 */

public class MainActivity extends BaseActivity {

    public static final int TAB_INDEX_HOME = 0;
    public static final int TAB_INDEX_CATEGORY = 1;
    public static final int TAB_INDEX_CHART = 2;
    public static final int TAB_INDEX_ARTICLE = 3;
    public static final int TAB_INDEX_USER = 4;

    private String DOWNLOAD_PATH = "/taodianke/apk/";

    private FragmentNavigator mNavigator;

    private RecyclerView main_bottom_menu_rv;

    private BottomMenuAdapter bottomMenuAdapter;

    private Bundle savedInstanceState;

    private ArrayList<Integer> indexList;

    private static class MyHandler extends Handler {

        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = mActivity.get();
            switch (msg.what) {
                case 1:
                    activity.bottomMenuClick(msg.arg1);
                    break;
            }
        }
    }

    private final MyHandler handler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.savedInstanceState = savedInstanceState;

        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        indexList = new ArrayList<Integer>();
        indexList.add(MainActivity.TAB_INDEX_HOME);
        indexList.add(MainActivity.TAB_INDEX_CATEGORY);
            indexList.add(MainActivity.TAB_INDEX_CHART);
//            indexList.add(MainActivity.TAB_INDEX_ARTICLE);
//        indexList.add(MainActivity.TAB_INDEX_USER);
        initNavigatorView(savedInstanceState);
        bottomMenuClick(TAB_INDEX_HOME);
    }

    private void initNavigatorView(Bundle savedInstanceState) {
        mNavigator = new FragmentNavigator(getSupportFragmentManager(),
                new FragmentAdapter(indexList), R.id.container, false);
        mNavigator.setDefaultPosition(0);
        mNavigator.onCreate(savedInstanceState);

        main_bottom_menu_rv = (RecyclerView) findViewById(R.id.main_bottom_menu_rv);
        main_bottom_menu_rv.setLayoutManager(new GridLayoutManager(this, indexList.size()));

        bottomMenuAdapter = new BottomMenuAdapter(this, makeBottomMenuText(), handler);
        LogUtils.e("makeBottomMenuText == " + makeBottomMenuText().size());
        main_bottom_menu_rv.setAdapter(bottomMenuAdapter);
    }

    /**
     * set current select index
     *
     * @param position
     */
    private void setCurrentTab(int position) {
        mNavigator.showFragment(position);
        bottomMenuAdapter.setSelect(position);
    }

    private void setOnclick(int position, int fragmentIndex) {
        setOnclick(position, fragmentIndex, "");
    }

    /**
     * @param position      fragment对应的position
     * @param fragmentIndex 自定义的fragment的index
     * @param paramStr
     */
    private void setOnclick(int position, int fragmentIndex, String paramStr) {
        boolean boo = getSupportFragmentManager().executePendingTransactions();
        if (boo) {
            switch (fragmentIndex) {
                case TAB_INDEX_HOME:
//                    homeFragment = (HomeFragment) mNavigator.getFragment(position);
//                    homeFragment.refresh();
                    break;
                case TAB_INDEX_CATEGORY:
                    break;
                case TAB_INDEX_CHART:
                    break;
                case TAB_INDEX_ARTICLE:
                    break;
                case TAB_INDEX_USER:
                    break;
            }
        }
    }

    private void bottomMenuClick(int index) {
//        if (TAB_INDEX_USER == indexList.get(index) && TextUtils.isEmpty(getToken())) {
//            openActivity(LoginActivity.class);
//        } else {
//            setCurrentTab(index);
//            setOnclick(index, indexList.get(index));
//        }
        setCurrentTab(index);
        setOnclick(index, indexList.get(index));
    }

    private ArrayList<Integer> getFontIdList() {
        ArrayList<Integer> fontStrIdList = new ArrayList<>();
        fontStrIdList.add(R.mipmap.ic_action_home);
        fontStrIdList.add(R.mipmap.ic_action_category);
        fontStrIdList.add(R.mipmap.ic_action_bargraph);
        fontStrIdList.add(R.mipmap.ic_action_document);
        fontStrIdList.add(R.mipmap.ic_action_user);
        return fontStrIdList;
    }

    private ArrayList<Integer> getFontIdListNormal() {
        ArrayList<Integer> fontStrIdList = new ArrayList<>();
        fontStrIdList.add(R.mipmap.ic_launcher);
        fontStrIdList.add(R.mipmap.ic_launcher);
        fontStrIdList.add(R.mipmap.ic_launcher);
        return fontStrIdList;
    }

    private LinkedHashMap<Integer, String> makeBottomMenuText() {
        // key:R.String.fontStrId,value:text
        LinkedHashMap<Integer, String> menuDataMap = new LinkedHashMap<>();
        String[] array = getResources().getStringArray(R.array.bottom_menu);
        ArrayList<Integer> fontStrIdList = getFontIdList();
//        if (null == getUserInfo() || TextUtils.isEmpty(getUserInfo().getPid())) {
//            array = getResources().getStringArray(R.array.bottom_menu_normal);
//            fontStrIdList = getFontIdListNormal();
//        }
        for (int i = 0; i < array.length; i++) {
            menuDataMap.put(fontStrIdList.get(i), array[i]);
        }
        return menuDataMap;
    }


    /**
     * 有参数 弹出更新提示
     */
    public void showUpdateDialog(final UpgradeModel upgradeBean) {
        if (!getFilePermission(this)) {
            return;
        }
        final WeakReference<Activity> weakReference = new WeakReference<Activity>(this);
        if (weakReference.get() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(weakReference.get());
            builder.setTitle(upgradeBean.getVersion() + getString(R.string.updateTip))
                    .setMessage(upgradeBean.getDescription())
                    .setPositiveButton(getString(R.string.download), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (!isDownloadManagerAvailable(weakReference.get())) {
                                LogUtils.e("isDownloadManagerAvailableisDownloadManagerAvailableisDownloadManagerAvailable");
                                showToastShort(getString(R.string.downloadManagerAvailable));
                            } else {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse(upgradeBean.getDownload_url());
                                intent.setData(content_url);
                                startActivity(intent);
                            }
                            String downloadUrl = upgradeBean.getDownload_url();
                            if (TextUtils.isEmpty(downloadUrl)) {
                                return;
                            }
                            String serviceString = Context.DOWNLOAD_SERVICE;
                            DownloadManager downloadManager = (DownloadManager) getSystemService(serviceString);

                            Uri uri = Uri.parse(downloadUrl);
                            grantUriPermission(BuildConfig.APPLICATION_ID, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            grantUriPermission(BuildConfig.APPLICATION_ID, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setDestinationInExternalPublicDir(DOWNLOAD_PATH, "tdk.apk");
                            request.setVisibleInDownloadsUi(true);
                            if (reference == 0) {
                                reference = downloadManager.enqueue(request);
                            }
                            showToastShort(getString(R.string.downloadStart));
                        }
                    }).setCancelable(!"Y".equals(upgradeBean.getIs_force()));
            if (!"Y".equals(upgradeBean.getIs_force())) {
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
            builder.create().show();
        }
    }

    private static boolean getFilePermission(Activity activity) {
        WeakReference<Activity> reference = new WeakReference<Activity>(activity);
        if (reference.get() != null) {
            if (ContextCompat.checkSelfPermission(reference.get(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(reference.get(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    // 下载中的id
    private static long reference = 0;

    public static class UpdateDownloadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            /**
             * android 7.0需要fileprovider读取权限
             */
            reference = 0;
            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                DownloadManager.Query query = new DownloadManager.Query();
                //在广播中取出下载任务的id
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);

                if (manager.getUriForDownloadedFile(id) != null) {
                    installAPK(context, getRealFilePath(context, manager.getUriForDownloadedFile(id)));
                } else {
                    Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                }
            } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
                long[] ids = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
                //点击通知栏取消下载
                manager.remove(ids);
                showToast(context, "取消下载", Toast.LENGTH_SHORT);
            }

        }

        private void installAPK(Context context, String path) {
            File file = new File(path);
            if (file.exists()) {
                openFile(file, context);
            } else {
                Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
            }
        }

        public String getRealFilePath(Context context, Uri uri) {
            if (null == uri) return null;
            final String scheme = uri.getScheme();
            String data = null;
            if (scheme == null)
                data = uri.getPath();
            else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
                data = uri.getPath();
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        if (index > -1) {
                            data = cursor.getString(index);
                        }
                    }
                    cursor.close();
                }
            }
            return data;
        }

        /**
         * 重点在这里
         */
        public void openFile(File var0, Context var1) {
            Intent var2 = new Intent();
            var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            var2.setAction(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uriForFile = FileProvider.getUriForFile(var1, var1.getApplicationContext().getPackageName() + ".FileProvider", var0);
                var2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                var2.setDataAndType(uriForFile, var1.getContentResolver().getType(uriForFile));
            } else {
                var2.setDataAndType(Uri.fromFile(var0), getMIMEType(var0));
            }
            try {
                var1.startActivity(var2);
            } catch (Exception var5) {
                var5.printStackTrace();
                Toast.makeText(var1, "没有找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
            }
        }

        public String getMIMEType(File var0) {
            String var1 = "";
            String var2 = var0.getName();
            String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
            var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
            return var1;
        }

        public static boolean deleteFileWithPath(String filePath) {
            SecurityManager checker = new SecurityManager();
            File f = new File(filePath);
            checker.checkDelete(filePath);
            if (f.isFile()) {
                f.delete();
                return true;
            }
            return false;
        }
    }

    public void checkUpdate(final boolean hasTips) {
        getApiRetrofit(new RequestCallBack<BaseModel<UpgradeModel>>() {
            @Override
            public void onResponse(BaseModel<UpgradeModel> response) {
                UpgradeModel upgradeBean = response.getData();
                if ("Y".equals(upgradeBean.getIs_upgrade())) {
                    showUpdateDialog(upgradeBean);
                } else if (hasTips) {
                    showToastShort(getString(R.string.newlyVersion));
                }
            }

            @Override
            public void onFailure(String code, Throwable t) {
                showToastShort(t.getMessage());
            }
        }, new TypeToken<BaseModel<UpgradeModel>>() {
        }.getType(), null, false).update(BuildConfig.VERSION_NAME);
    }

    /**
     * 下载管理器是否启用
     *
     * @param context
     * @return
     */
    private boolean isDownloadManagerAvailable(Context context) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                    || context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED_USER
                    || context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED
                    || context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {

                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isExit = false;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    isExit = false;
                    break;
            }

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, getResources().getString(R.string.confirmExit), Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
//            AppApplication.getInstance().exit();
            finish();
        }
    }
}
