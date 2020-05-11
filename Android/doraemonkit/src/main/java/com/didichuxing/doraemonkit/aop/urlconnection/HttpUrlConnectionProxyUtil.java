package com.didichuxing.doraemonkit.aop.urlconnection;

import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.DoraemonInterceptor;
import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.DoraemonWeakNetworkInterceptor;
import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.LargePictureInterceptor;
import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.MockInterceptor;
import com.didichuxing.doraemonkit.okgo.DokitOkGo;

import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2019-12-16-14:54
 * 描    述：ams 动态插入代码
 * 修订历史：
 * ================================================
 */
public class HttpUrlConnectionProxyUtil {
    //private static final String TAG = "HttpUrlConnectionProxyUtil";
    private static String[] hosts = new String[]{"amap.com"};

    public static URLConnection proxy(URLConnection urlConnection) {
        try {
            String host = HttpUrl.parse(urlConnection.getURL().toString()).host();
            if (isIgnore(host)) {
                return urlConnection;
            }
            return createOkHttpURLConnection(urlConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlConnection;
    }


    private static URLConnection createOkHttpURLConnection(URLConnection urlConnection) throws Exception {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //不需要再重复添加拦截器 因为已经通过字节码主如果拦截器了
        //addInterceptor(builder);
        OkHttpClient mClient = builder
                .retryOnConnectionFailure(true)
                .readTimeout(DokitOkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .writeTimeout(DokitOkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .connectTimeout(DokitOkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .build();

        String strUrl = urlConnection.getURL().toString();
        URL url = new URL(strUrl);
        String protocol = url.getProtocol().toLowerCase();
        if (protocol.equalsIgnoreCase("http")) {
            return new ObsoleteUrlFactory.OkHttpURLConnection(url, mClient);
        }

        if (protocol.equalsIgnoreCase("https")) {
            return new ObsoleteUrlFactory.OkHttpsURLConnection(url, mClient);
        }

        return urlConnection;

    }

    private static void addInterceptor(OkHttpClient.Builder builder) {
        // 判断当前是否已经添加了拦截器，如果已添加则返回
        for (Interceptor interceptor : builder.interceptors()) {
            if (interceptor instanceof MockInterceptor) {
                return;
            }
        }

        builder
                //添加mock拦截器
                .addInterceptor(new MockInterceptor())
                //添加大图检测拦截器
                .addInterceptor(new LargePictureInterceptor())
                //添加dokit拦截器
                .addInterceptor(new DoraemonInterceptor())
                //添加弱网 拦截器
                .addNetworkInterceptor(new DoraemonWeakNetworkInterceptor());
    }

    /**
     * 判断是否过滤指定的host
     *
     * @param host
     * @return
     */
    private static boolean isIgnore(String host) {
        for (String jumpHost : hosts) {
            if (host.contains(jumpHost)) {
                return true;
            }
        }
        return false;
    }
}
