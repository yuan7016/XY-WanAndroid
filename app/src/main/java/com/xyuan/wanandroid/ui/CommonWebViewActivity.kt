package com.xyuan.wanandroid.ui


import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.base.BaseActivity
import com.xyuan.wanandroid.constant.PathManager
import com.xyuan.wanandroid.util.AppLog
import com.xyuan.wanandroid.util.HtmlUtil
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.common_tool_bar_layout.*

/**
 * Created by YuanZhiQiang on  2019/4/4 0004 <br/>
 *  desc: WebView
 */
@Route(path= PathManager.WEBVIEW_ACTIVITY_PATH)
class CommonWebViewActivity : BaseActivity() {
    private  var mWebView: WebView? = null

    @Autowired
    lateinit var loadUrl : String
    @Autowired
    lateinit var title : String

    override fun getContentLayoutId(): Int {
        return R.layout.activity_web_view
    }

    override fun initView() {

        mWebView = WebView(this)

        frameLayoutWebView.addView(mWebView)

        initToolBar(toolbarCommon,HtmlUtil.htmlDecode(title))

        initWebView()
    }

    private fun initWebView() {
        //不现实水平滚动条
        mWebView!!.isHorizontalScrollBarEnabled = false
        //不现实垂直滚动条
        mWebView!!.isVerticalScrollBarEnabled = false
        mWebView!!.requestFocusFromTouch()

        val webSettings = mWebView!!.settings
        //支持缩放，默认为true。
        webSettings.setSupportZoom(false)
        //调整图片至适合webview的大小
        webSettings.useWideViewPort = true
        // 缩放至屏幕的大小
        webSettings.loadWithOverviewMode = true
        //设置默认编码
        webSettings.defaultTextEncodingName = "utf-8"
        //设置自动加载图片
        webSettings.loadsImagesAutomatically = true
        //开启javascript
        webSettings.javaScriptEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.displayZoomControls = false //是否显示缩放按钮
        webSettings.domStorageEnabled = true
        webSettings.setSupportMultipleWindows(true)
        webSettings.setAppCacheEnabled(true)
        val cachePath = filesDir.absolutePath + "/WebViewCache"
        webSettings.setAppCachePath(cachePath)
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        //5.0及以上webview不支持http和https混合模式，需要通过配置来开启混合模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

       mWebView!!.webViewClient = MyWebViewClient()
       mWebView!!.webChromeClient = MyWebChromeClient()

    }

    override fun initData() {

        //加载webView内容
        if (loadUrl.isNotEmpty()){
            mWebView!!.loadUrl(loadUrl)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView!!.canGoBack()) {
            mWebView!!.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onDestroy() {
        if (mWebView != null) {
            mWebView?.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            mWebView?.clearHistory()
            frameLayoutWebView.removeView(mWebView)
            mWebView?.destroy()
            mWebView = null
        }

        super.onDestroy()
    }


    inner class MyWebViewClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            AppLog.w("====WebViewClient===onPageStarted===")
            progressBar.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            AppLog.w("====WebViewClient===onPageFinished===title=${view!!.title}")
            progressBar.visibility = View.GONE
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            //super.onReceivedSslError(view, handler, error)
            handler!!.proceed()
        }

    }

    inner class MyWebChromeClient : WebChromeClient(){

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressBar.progress = newProgress

            if (newProgress == 100){
                progressBar.visibility = View.GONE
            }
        }

    }
}