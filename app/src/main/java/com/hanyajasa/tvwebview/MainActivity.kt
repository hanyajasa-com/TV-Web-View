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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.material3.LinearProgressIndicator
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
                var progress by remember { mutableFloatStateOf(0f) }
                var isVisible by remember { mutableStateOf(true) }

                Box(modifier = Modifier.fillMaxSize()) {
                    IndiHomeTVWebView(
                        url = "https://www.indihometv.com/livetv/antv",
                        onProgressChanged = { newProgress ->
                            progress = newProgress / 100f
                            isVisible = newProgress < 100
                        },
                        onWebViewCreated = { view ->
                            webView = view
                        }
                    )

                    if (isVisible) {
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp),
                            color = Color.Red,
                            trackColor = Color.Transparent
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun IndiHomeTVWebView(
    url: String,
    onProgressChanged: (Int) -> Unit,
    onWebViewCreated: (WebView) -> Unit
) {
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
                
                @SuppressLint("ClickableViewAccessibility")
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
                        
                        // Inject CSS untuk menyembunyikan header, footer, dan elemen non-video
                        val css = """
                            header, footer, .header, .footer, .navbar, .nav-container, 
                            .bottom-nav, .breadcrumb, .vjs-control-bar-background,
                            .channel-detail-info, .section-channel, .p-4, .py-4,
                            .mt-4, .mb-4, .grid-cols-1 { display: none !important; }
                            
                            body, html { overflow: hidden !important; background: black !important; }
                            
                            #video-container, .video-js, #player, .player-container, 
                            .video-wrapper, [id*='player'], [class*='player'] { 
                                position: fixed !important;
                                top: 0 !important;
                                left: 0 !important;
                                width: 100vw !important;
                                height: 100vh !important;
                                z-index: 99999 !important;
                                margin: 0 !important;
                                padding: 0 !important;
                            }
                        """.trimIndent()
                        
                        val js = "var style = document.createElement('style');" +
                                "style.innerHTML = '$css';" +
                                "document.head.appendChild(style);"
                        
                        view?.evaluateJavascript(js, null)
                    }
                }
                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        onProgressChanged(newProgress)
                    }

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