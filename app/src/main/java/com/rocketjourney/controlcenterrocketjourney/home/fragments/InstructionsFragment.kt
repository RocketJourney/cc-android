package com.rocketjourney.controlcenterrocketjourney.home.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rocketjourney.controlcenterrocketjourney.R
import android.content.Intent
import android.net.Uri


class InstructionsFragment : Fragment() {

    private val URL = "https://assets.rocketjourney.com/control-center/assets/staff_guide/main_es.html"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_instructions, container, false)

        val webView = view.findViewById<WebView>(R.id.webViewInstructions)

        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true

        val webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)

                return true
            }
        }

        webView.webViewClient = webViewClient
        webView.loadUrl(URL)

        return view
    }


}