package access.com.baichuantest.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import java.lang.reflect.Field;

import access.com.baichuantest.R;
import butterknife.ButterKnife;


/**
 * Created by xufangqiang on 2017/1/3.
 */

public class Fragment1 extends BaseFragment {

    private Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
//        onNearInteractionListener = ((OnNearInteractionListener) activity);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
//        ImmersionBar.setTitleBar(getActivity(), toolbar);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_test;
    }

    private void initView() {
    }

    public void setZoomControlGone(View view) {
        Class classType;
        Field field;
        try {
            classType = WebView.class;
            field = classType.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(view);
            mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
            try {
                field.set(view, mZoomButtonsController);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
