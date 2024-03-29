package com.sidapps.tictactoeai.logic

import java.awt.Desktop
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException

object WebBrowser {
    fun open(url: String) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(URI(url))
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            } catch (e: UnsupportedOperationException) {
                openUrlWithRuntime(url)
            }
        } else {
            openUrlWithRuntime(url)
        }
    }

    private fun openUrlWithRuntime(url: String) {
        val os = System.getProperty("os.name").toLowerCase()
        val runtime = Runtime.getRuntime()
        try {
            when {
                os.contains("win") -> runtime.exec("rundll32 url.dll,FileProtocolHandler $url")
                os.contains("mac") -> runtime.exec("open $url")
                os.contains("nix") || os.contains("nux") -> runtime.exec("xdg-open $url")
                else -> println("Cannot open web browser on this platform")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}