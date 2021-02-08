package com.teema.my.changethemeexample

import android.content.Context
import android.graphics.Color
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.teema.my.changethemeexample.theme.DynamicTheme
import java.io.File
import java.io.FileReader

class ThemeLoader(private val context: Context, private val themeId: String) {

    private val map: HashMap<String, String>

    init {
        val metaFile = File("${context.filesDir}/theme_$themeId/meta-data.json")
        map = Gson().fromJson(JsonReader(FileReader(metaFile)), HashMap::class.java)
    }

    fun copyToTheme(theme: DynamicTheme) {
        theme.setPrimaryColor(Color.parseColor(map["primary_color"]))
        theme.setSecondaryColor(Color.parseColor(map["secondary_color"]))
        theme.notifyThemeChange()
    }

}