package com.hanyajasa.tvwebview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.view.View
import android.webkit.PermissionRequest
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.hanyajasa.tvwebview.ui.theme.TVWebViewTheme

class MainActivity : ComponentActivity() {
    private var webView: WebView? = null

    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView?.canGoBack() == true) {
                    webView?.goBack()
                } else {
                    finish()
                }
            }
        })

        setContent {
            TVWebViewTheme {
                // Menggunakan Box standar agar tidak mengintersepsi event klik/sentuh dari mouse emulator
                Box(modifier = Modifier.fillMaxSize()) {
                    IndiHomeTVWebView("https://www.indihometv.com/livetv/antv") { view ->
                        webView = view
                    }
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun IndiHomeTVWebView(url: String, onWebViewCreated: (WebView) -> Unit) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                
                // Memastikan WebView dapat menerima input dari mouse/keyboard
                isFocusable = true
                isFocusableInTouchMode = true
                isClickable = true
                
                setOnTouchListener { v, _ ->
                    v.requestFocus()
                    false
                }

                // Aktifkan Cookie agar sesi login atau data player tersimpan
                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                cookieManager.setAcceptThirdPartyCookies(this, true)
                
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        // Memastikan link tetap dibuka di WebView aplikasi
                        return false 
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        // Meminta fokus agar input mouse/keyboard langsung aktif ke konten web
                        view?.requestFocus()
                    }
                }
                webChromeClient = object : WebChromeClient() {
                    override fun onPermissionRequest(request: PermissionRequest?) {
                        // Memberikan izin otomatis untuk Protected Content/Eme (Penting untuk Video Player)
                        request?.grant(request.resources)
                    }
                }
                
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    databaseEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    mediaPlaybackRequiresUserGesture = false
                    javaScriptCanOpenWindowsAutomatically = true
                    
                    // Izinkan konten campuran (HTTP di dalam HTTPS) yang sering ada di situs streaming
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    
                    // User Agent Desktop terbaru untuk interaksi yang lebih baik
                    userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36"
                }
                
                onWebViewCreated(this)
                loadUrl(url)
            }
        }
    )
}