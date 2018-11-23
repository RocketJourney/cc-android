package com.rocketjourney.controlcenterrocketjourney.structure.views

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import kotlinx.android.synthetic.main.activity_web_view.*;
import kotlinx.android.synthetic.main.component_toolbar_title.view.*

class WebViewActivity : AppCompatActivity() {

    companion object {
        val EXTRA_TITLE = "EXTRA_TITLE"
        val EXTRA_URL = "EXTRA_URL"
    }

    lateinit var title: String
    lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        title = intent.getStringExtra(EXTRA_TITLE)
        url = intent.getStringExtra(EXTRA_URL)

        setSupportActionBar(componentToolbar.toolbar)
        Utils.hideToolbarTitle(supportActionBar)

        componentToolbar.toolbar.setNavigationIcon(R.drawable.ic_close_yellow)
        componentToolbar.toolbar.setNavigationOnClickListener(View.OnClickListener { finish() })

        componentToolbar.textViewToolbarTitle.text = title

        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true

        val webViewClient = object : WebViewClient() {}

        webView.webViewClient = webViewClient
        webView.loadUrl(url)
    }
}