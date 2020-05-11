package com.didichuxing.doraemonkit.constant

import com.blankj.utilcode.util.PathUtils
import com.didichuxing.doraemonkit.BuildConfig
import com.didichuxing.doraemonkit.config.GlobalConfig
import com.didichuxing.doraemonkit.kit.AbstractKit
import com.didichuxing.doraemonkit.kit.dbdebug.DbDebugFragment
import com.didichuxing.doraemonkit.kit.network.bean.WhiteHostBean
import com.didichuxing.doraemonkit.kit.network.room_db.DokitDbManager
import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem
import com.didichuxing.doraemonkit.model.ActivityLifecycleInfo
import java.io.File
import java.lang.ref.WeakReference

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2019-12-19-10:21
 * 描    述：
 * 修订历史：
 * ================================================
 */
object DokitConstant {

    const val GROUP_ID_PLATFORM = "dk_category_platform"
    const val GROUP_ID_COMM = "dk_category_comms"
    const val GROUP_ID_WEEX = "dk_category_weex"
    const val GROUP_ID_PERFORMANCE = "dk_category_performance"
    const val GROUP_ID_UI = "dk_category_ui"

    val SYSTEM_KITS_BAK_PATH: String by lazy {
        "${PathUtils.getInternalAppFilesPath()}${File.separator}system_kit_bak_${BuildConfig.DOKIT_VERSION}.json"
    }

    /**
     * 工具面板RV上次的位置
     */
    var TOOL_PANEL_RV_LAST_DY = 0


    /**
     * 全局的Kits
     */
    @JvmField
    val GLOBAL_KITS: LinkedHashMap<String, MutableList<KitWrapItem>> = LinkedHashMap()

    @JvmField
    val GLOBAL_SYSTEM_KITS: LinkedHashMap<String, MutableList<KitWrapItem>> = LinkedHashMap()

    /**
     * 产品id
     */
    @JvmField
    var PRODUCT_ID = ""

    /**
     * 是否处于健康体检中
     */
    @JvmField
    var APP_HEALTH_RUNNING = GlobalConfig.getAppHealth()

    /**
     * 是否是普通的浮标模式
     */
    @JvmField
    var IS_NORMAL_FLOAT_MODE = true

    /**
     * 是否显示icon主入口
     */
    @JvmField
    var AWAYS_SHOW_MAIN_ICON = true

    /**
     * icon主入口是否处于显示状态
     */
    @JvmField
    var MAIN_ICON_HAS_SHOW = false

    /**
     * 流量监控白名单
     */
    @JvmField
    var WHITE_HOSTS = mutableListOf<WhiteHostBean>()

    /**
     * 全局DBDebugFragment
     */
    @JvmField
    var DB_DEBUG_FRAGMENT: WeakReference<DbDebugFragment>? = null


    @JvmField
    var ACTIVITY_LIFECYCLE_INFOS = mutableMapOf<String, ActivityLifecycleInfo>()


    /**
     * 判断接入的是否是滴滴内部的rpc sdk
     *
     * @return
     */
    @JvmStatic
    val isRpcSDK: Boolean
        get() {
            val isRpcSdk: Boolean
            isRpcSdk = try {
                Class.forName("com.didichuxing.doraemonkit.DoraemonKitRpc")
                true
            } catch (e: ClassNotFoundException) {
                false
            }
            return isRpcSdk
        }

    /**
     * 兼容滴滴内部外网映射环境  该环境的 path上会多一级/kop_xxx/路径
     *
     * @param oldPath
     * @param fromSDK
     * @return
     */
    @JvmStatic
    fun dealDidiPlatformPath(oldPath: String, fromSDK: Int): String {
        if (fromSDK == DokitDbManager.FROM_SDK_OTHER) {
            return oldPath
        }
        var newPath = oldPath
        //包含多级路径
        if (oldPath.contains("/kop") && oldPath.split("\\/").toTypedArray().size > 1) {
            //比如/kop_stable/a/b/gateway 分解以后为 "" "kop_stable" "a" "b" "gateway"
            val childPaths = oldPath.split("\\/").toTypedArray()
            val firstPath = childPaths[1]
            if (firstPath.contains("kop")) {
                newPath = oldPath.replace("/$firstPath", "")
            }
        }
        return newPath
    }
}