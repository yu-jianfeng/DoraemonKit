package com.didichuxing.doraemonkit.kit.alignruler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;

/**
 * Created by jintai on 2019/09/26.
 */

public class AlignRulerLineDokitView extends AbsDokitView implements AlignRulerMarkerDokitView.OnAlignRulerMarkerPositionChangeListener {
    private AlignRulerMarkerDokitView mMarker;
    private AlignLineView mAlignInfoView;

    @Override
    public void onCreate(Context context) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMarker.removePositionChangeListener(this);
    }

    @Override
    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_align_ruler_line, view, false);
    }


    @Override
    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.height = DokitViewLayoutParams.MATCH_PARENT;
        params.width = DokitViewLayoutParams.MATCH_PARENT;
    }

    @Override
    public void onViewCreated(FrameLayout view) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mMarker = (AlignRulerMarkerDokitView) DokitViewManager.getInstance().getDokitView(ActivityUtils.getTopActivity(), AlignRulerMarkerDokitView.class.getSimpleName());
                if (mMarker != null) {
                    mMarker.addPositionChangeListener(AlignRulerLineDokitView.this);
                }
            }
        }, 100);
        setDokitViewNotResponseTouchEvent(getRootView());
        mAlignInfoView = findViewById(R.id.info_view);
    }

    @Override
    public void onPositionChanged(int x, int y) {
        /**
         * 限制边界
         */
        if (!isNormalMode()) {
            int iconSize = ConvertUtils.dp2px(30);
            if (y <= iconSize) {
                y = iconSize;
            }

            if (ScreenUtils.isPortrait()) {
                if (y >= getScreenLongSideLength() - iconSize) {
                    y = getScreenLongSideLength() - iconSize;
                }
            } else {
                if (y >= getScreenShortSideLength() - iconSize) {
                    y = getScreenShortSideLength() - iconSize;
                }
            }


            if (x <= iconSize) {
                x = iconSize;
            }
            if (ScreenUtils.isPortrait()) {
                if (x >= getScreenShortSideLength() - iconSize) {
                    x = getScreenShortSideLength() - iconSize;
                }
            } else {
                if (x >= getScreenLongSideLength() - iconSize) {
                    x = getScreenLongSideLength() - iconSize;
                }
            }
        }


        mAlignInfoView.showInfo(x, y);
    }

    @Override
    public boolean canDrag() {
        return false;
    }

    @Override
    public boolean restrictBorderline() {
        return true;
    }
}