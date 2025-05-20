package com.hec9527.battlecity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {
    // @SuppressLint("SetJavaScriptEnabled")
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        WebView.setWebContentsDebuggingEnabled(true)
        enableEdgeToEdge()
        hideSystemUI()
        setContentView(R.layout.activity_main)
        val webView = findViewById<WebView>(R.id.webView)

        webView.apply {
            webViewClient = LocalWebViewClient()
            settings.javaScriptEnabled = true
            settings.allowFileAccess = true
            settings.domStorageEnabled = true
            settings.allowFileAccessFromFileURLs = true
            settings.allowUniversalAccessFromFileURLs = true
            loadUrl("http://battlecity.hec9527.com/index.html")
        }
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.statusBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
