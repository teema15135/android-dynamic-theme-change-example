package com.teema.my.changethemeexample

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.zip.ZipFile

class ThemeDownloadHelper(private val context: Context) {

    fun getDownloadedThemeList(): Array<String> {
        val filesDir = context.filesDir
        return filesDir.list() ?: emptyArray()
    }

    fun getDownloadedThemeIdList(): Array<String> =
        getDownloadedThemeList().map { it.split("_")[1] }.toTypedArray()

    fun downloadTheme(themeId: String): Boolean {
        Log.d("ThemeDownloadHelper", "http://172.16.10.56:8000/theme_$themeId.zip")
        try {
            val url = URL("http://172.16.10.56:8000/theme_$themeId.zip")
            url.openStream().use { input ->
                val filePath = context.filesDir.toString() + "/theme_" + themeId + ".zip"
                FileOutputStream(filePath).use { output ->
                    input.copyTo(output)
                    Log.d("ThemeDownloadHelper", "Save to $filePath")
                    return unzipTheme(themeId)
                }
            }
        } catch (ex: Exception) {
            return false
        }
    }

    private fun unzipTheme(themeId: String): Boolean {
        val filesDir = context.filesDir.toString()
        val filePath = "$filesDir/theme_$themeId.zip"
        try {
            ZipFile(filePath).use { zip ->
                for (entry in zip.entries().asSequence()) {
                    zip.getInputStream(entry).use { input ->
                        File("$filesDir/theme_$themeId").mkdirs()
                        File("$filesDir/theme_$themeId/${entry.name}").outputStream().use { output ->
                            input.copyTo(output)
                            Log.d(
                                "ThemeDownloadHelper",
                                "Unzip to $filesDir/theme_$themeId/${entry.name}"
                            )
                        }
                    }
                }
                File(filePath).delete()
                return true
            }
        } catch (ex: Exception) {
            return false
        }
    }
}