package com.didichuxing.doraemonkit.kit.largepicture;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.aop.DokitPluginConfig;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.constant.FragmentIndex;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.kit.Category;
import com.didichuxing.doraemonkit.util.DokitUtil;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2019-09-24-17:05
 * 描    述：网络大图检测功能入口
 * 修订历史：
 * ================================================
 */
public class LargePictureKit extends AbstractKit {


    @Override
    public int getName() {
        return R.string.dk_frameinfo_big_img;
    }

    @Override
    public int getIcon() {
        return R.mipmap.dk_performance_large_picture;
    }

    @Override
    public void onClick(Context context) {
        if (!DokitPluginConfig.SWITCH_DOKIT_PLUGIN) {
            ToastUtils.showShort(DokitUtil.getString(R.string.dk_plugin_close_tip));
            return;
        }

        if (!DokitPluginConfig.SWITCH_BIG_IMG) {
            ToastUtils.showShort(DokitUtil.getString(R.string.dk_plugin_big_img_close_tip));
            return;
        }


        startUniversalActivity(context,FragmentIndex.FRAGMENT_LARGE_PICTURE);
    }

    @Override
    public void onAppInit(Context context) {

    }

    @Override
    public boolean isInnerKit() {
        return true;
    }

    @Override
    public String innerKitId() {
        return "dokit_sdk_performance_ck_img";
    }
}
