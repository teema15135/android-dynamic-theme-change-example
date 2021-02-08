package com.teema.my.changethemeexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.teema.my.changethemeexample.databinding.ActivityMainBinding
import com.teema.my.changethemeexample.theme.DynamicTheme


class MainActivity() : AppCompatActivity() {

    val theme: DynamicTheme by lazy { DynamicTheme() }

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val themeAvailable = arrayOf("01", "02", "03")
        val themeDownloadHelper = ThemeDownloadHelper(this)

        binding.theme = theme
        binding.changeThemeButton.setOnClickListener {
            if (theme.currentTheme == DynamicTheme.Theme.PINK) {
                theme.setTheme(DynamicTheme.Theme.GREEN)
            } else {
                theme.setTheme(DynamicTheme.Theme.PINK)
            }

            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            themeDownloadHelper.downloadTheme("01")
            for (themeDir in themeDownloadHelper.getDownloadedThemeIdList()) {
                Log.d("MainActivity", themeDir)
            }
//            themeDownloadHelper.unzipTheme("theme_01")
        }
        binding.changeToDownloadedThemeButton.setOnClickListener {
            val themeLoader = ThemeLoader(this, "01")
            themeLoader.copyToTheme(theme)
        }

        binding.listView.adapter = ThemeAdapter(context = this,
            resource = R.layout.row_item,
            dataSet = themeAvailable,
            downloadedSet = themeDownloadHelper.getDownloadedThemeIdList(),
            onPressDownload = { position ->
                if (themeDownloadHelper.downloadTheme(themeAvailable[position])) {
                    return@ThemeAdapter true
                } else {
                    Toast.makeText(this, "Connection problem!", Toast.LENGTH_LONG).show()
                    return@ThemeAdapter false
                }
            },
            onPressUse = { position ->
                val themeLoader = ThemeLoader(this, themeAvailable[position])
                themeLoader.copyToTheme(theme)
                return@ThemeAdapter true
            })
    }
}