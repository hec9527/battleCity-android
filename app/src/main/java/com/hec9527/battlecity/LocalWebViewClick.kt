package com.hec9527.battlecity

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.FileNotFoundException

class LocalWebViewClient : WebViewClient() {

    companion object{
        const val TAG = "LocalWebViewClient"
    }
    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        val url = request?.url.toString()

        Log.i(TAG,"拦截请求：$url")

        // 只拦截 http/https 请求
        if (url.startsWith("http://") || url.startsWith("https://")) {
            // 去掉协议和域名，得到相对路径
            val path = request?.url?.path?.removePrefix("/") ?: return null
            try {
                // 尝试打开 assets 下的资源
                val inputStream = view?.context?.assets?.open(path)
                val mimeType = getMimeTypeFromPath(path)
                Log.i(TAG,"返回响应:$url $mimeType")
                return WebResourceResponse(
                    mimeType,
                    "utf-8",
                    inputStream
                )
            } catch (e: FileNotFoundException) {
                Log.w(TAG,"没有资源:$url")
                // 本地没有该资源，继续正常加载
                return null
            }
        }
        return null
    }

    // 根据文件后缀返回常见的 MIME type
    private fun getMimeTypeFromPath(path: String): String {
        return when {
            path.endsWith(".html") -> "text/html"
            path.endsWith(".js") -> "application/javascript"
            path.endsWith(".css") -> "text/css"
            path.endsWith(".json") -> "application/json"
            path.endsWith(".png") -> "image/png"
            path.endsWith(".jpg") || path.endsWith(".jpeg") -> "image/jpeg"
            path.endsWith(".gif") -> "image/gif"
            path.endsWith(".svg") -> "image/svg+xml"
            path.endsWith(".mp3") -> "audio/mpeg"
            path.endsWith(".woff") -> "font/woff"
            path.endsWith(".woff2") -> "font/woff2"
            path.endsWith(".ttf") -> "font/ttf"
            else -> "application/octet-stream"
        }
    }
}