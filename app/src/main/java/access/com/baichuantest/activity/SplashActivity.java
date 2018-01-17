package access.com.baichuantest.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import access.com.baichuantest.AppApplication;
import access.com.baichuantest.R;
import access.com.baichuantest.data.Key;
import access.com.baichuantest.model.LoadImageInfo;
import access.com.baichuantest.utils.SharedPreferencesUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kain on 2015/12/16.
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.img_pic)
    ImageView img_pic;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.splash_guide_vp)
    ViewPager splash_guide_vp;
    @BindView(R.id.splash_index_rv)
    RecyclerView splash_index_rv;
    @BindView(R.id.cut_down_tv)
    TextView cut_down_tv;

    private boolean isStartActivity = false;

    private int cutDown = 5;

    private static class MyHandler extends Handler {

        private final WeakReference<SplashActivity> mActivity;

        public MyHandler(SplashActivity activity) {
            mActivity = new WeakReference<SplashActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity activity = mActivity.get();
            switch (msg.what) {
                case 1:
                    activity.jumpBanner();
                    break;

            }
        }
    }

    private final MyHandler handler = new MyHandler(this);

    private void jumpBanner() {
        if (cutDown < 2) {
            openMainActivity();
        } else {
            cutDown--;
            cut_down_tv.setText(cutDown + "s跳过");
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        SharedPreferencesUtil.init(getApplicationContext());

        WeakReference<Activity> weakReference = new WeakReference<Activity>(this);
        ImmersionBar.with(weakReference.get()).navigationBarColor(R.color.colorPrimaryDark).navigationBarWithKitkatEnable(false).init();
        ImmersionBar.setTitleBar(weakReference.get(), toolbar);

        if(null != getSupportActionBar()){
            getSupportActionBar().hide();
        }

        //
        boolean hasShowGuide = SharedPreferencesUtil.getBoolean(Key.sharePreferenceName, Key.SHARE_PREFERENCE_SPLASH, true);
        splash_guide_vp.setVisibility(hasShowGuide ? View.VISIBLE : View.GONE);
//        if (hasShowGuide) {
//            initGuideViewPager();
//        } else {
//            init();
//        }
        init();
        loadInit();
    }

    private void init() {
        LoadImageInfo imageInfo = getImageInfo();
        if (imageInfo != null && (!TextUtils.isEmpty(imageInfo.getUrl()))) {

            final Callback callback = new Callback() {
                @Override
                public void onSuccess() {
                    cut_down_tv.setVisibility(View.VISIBLE);
                    handler.sendEmptyMessageDelayed(1, 1000);
                }

                @Override
                public void onError() {
                    cut_down_tv.setVisibility(View.GONE);
                    openMainActivity();
                }
            };
            Picasso.with(this).load(imageInfo.getUrl()).placeholder(R.drawable.splash).into(img_pic,callback);
        } else {
            Picasso.with(this).load(R.drawable.splash).into(img_pic);
            initView();
        }
    }

//    private void initGuideViewPager() {
//        SharedPreferencesUtil.putBoolean(
//                Key.sharePreferenceName, Key.SHARE_PREFERENCE_SPLASH, false);
//        final ArrayList<Integer> images = new ArrayList<>();
//        final ArrayList<View> viewList = new ArrayList<>();
//
//        images.add(R.drawable.img_guide_1);
//        images.add(R.drawable.img_guide_2);
//        images.add(R.drawable.img_guide_3);
//        images.add(R.drawable.img_guide_4);
//        for (int i = 0; i < images.size(); i++) {
//            viewList.add(getGuideIv());
//        }
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(viewList);
//        splash_guide_vp.setAdapter(viewPagerAdapter);
//        splash_index_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        final IndexAdapter indexAdapter = new IndexAdapter(viewList.size());
//        splash_index_rv.setAdapter(indexAdapter);
//
//        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                indexAdapter.setSelection(position);
//                GifImageView iv = (GifImageView) viewList.get(position).findViewById(R.id.iv);
//                if (position == images.size() - 1) {
//                    iv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            openMainActivity();
//                        }
//                    });
//                }
//                //resource (drawable or raw)
//                try {
//                    GifDrawable gifFromResource = new GifDrawable(getResources(), images.get(position));
//                    iv.setImageDrawable(gifFromResource);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        };
//        splash_guide_vp.addOnPageChangeListener(onPageChangeListener);
//        onPageChangeListener.onPageSelected(0);
//    }

    @OnClick(R.id.img_pic)
    public void startActive() {
//        String url = null == getImageInfo() ? "" : getImageInfo().getAction_url();
//        if (!TextUtils.isEmpty(url)) {
//            isStartActivity = true;
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            if (url.startsWith("taodianke")) {
//                startActivity(intent);
//            } else {
//                intent = new Intent(this, WebViewActivity.class);
//                intent.putExtra("url", url);
//                startActivity(intent);
//            }
//            finish();
//        }
    }

//    private View getGuideIv() {
//        View view = LayoutInflater.from(this).inflate(R.layout.view_gif_iv, null);
//        return view;
//    }

    private void initView() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (!isStartActivity) {
                    openMainActivity();
                }
            }
        }, 2500L);
    }

    private void openMainActivity() {
        startActivity(new Intent(this,MainActivity.class));
        splash_guide_vp.removeAllViews();
        finish();
    }

    private void loadInit() {
//        ApiRetrofitImpl apiRetrofit = new ApiRetrofitImpl();
//        apiRetrofit.setCallBack(new RequestCallBack<BaseModel<LoadImageInfo>>() {
//            @Override
//            public void onResponse(BaseModel<LoadImageInfo> response) {
//                LoadImageInfo loadImageInfo = response.getData();
//                LoadImageInfo lastInfo = getImageInfo();
//                if (null != loadImageInfo && null != loadImageInfo.getNew_notice()) {
//                    if (null == lastInfo || lastInfo.getLook_time() < 1) {
////                        LogUtils.e("lastInfo.getLook_time() == " + lastInfo.getLook_time());
//                        loadImageInfo.setLook_time(System.currentTimeMillis() / 1000);
//                    } else {
//                        loadImageInfo.setLook_time(lastInfo.getLook_time());
//                    }
//                }
//                LogUtils.e("loadImageInfo == " + loadImageInfo.getLook_time());
//                setImageInfo(loadImageInfo);
//            }
//
//            @Override
//            public void onFailure(String code, Throwable t) {
//            }
//        });
//        apiRetrofit.setType(new TypeToken<BaseModel<LoadImageInfo>>() {
//        }.getType());
//        apiRetrofit.setFinishListener(new ApiRetrofitImpl.RequestFinishListener() {
//            @Override
//            public void requestFinished(String code) {
//
//            }
//        });
//        apiRetrofit.loadInit();
    }

    @OnClick(R.id.cut_down_tv)
    public void jumpThis(){
        openMainActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private LoadImageInfo getImageInfo() {
        return SharedPreferencesUtil.getPreferences(AppApplication.getInstance().getApplicationContext(),
                Key.sharePreferenceName, Key.sharePreferenceImageInfo);
    }

    /**
     * 缓存欢迎页数据
     *
     * @param imageInfo 需要重置的用户数据
     */
    public void setImageInfo(LoadImageInfo imageInfo) {
        SharedPreferencesUtil.putPreferences(AppApplication.getInstance().getApplicationContext(), Key.sharePreferenceName,
                Key.sharePreferenceImageInfo, imageInfo);
    }
}
