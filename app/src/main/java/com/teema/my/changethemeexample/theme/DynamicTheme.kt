package com.teema.my.changethemeexample.theme

import android.graphics.Color
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.teema.my.changethemeexample.BR

class DynamicTheme: BaseObservable() {

    var currentTheme = Theme.PINK

    private var primaryColor = Color.parseColor("#ff9292")
    private var secondaryColor = Color.parseColor("#ffb4b4")

    @Bindable
    fun getPrimaryColor() = primaryColor
    @Bindable
    fun getSecondaryColor() = secondaryColor

    fun setPrimaryColor(value: Int) {
        primaryColor = value
    }

    fun setSecondaryColor(value: Int) {
        secondaryColor = value
    }

    fun setTheme(theme: Theme) {
        when(theme) {
            Theme.PINK -> {
                primaryColor = Color.parseColor("#ff9292")
                secondaryColor = Color.parseColor("#ffb4b4")
            }
            Theme.GREEN -> {
                primaryColor = Color.parseColor("#75cfb8")
                secondaryColor = Color.parseColor("#bbdfc8")
            }
        }
        currentTheme = theme
        notifyThemeChange()
    }

    fun notifyThemeChange() {
        notifyPropertyChanged(BR.primaryColor)
        notifyPropertyChanged(BR.secondaryColor)
    }

    enum class Theme {
        PINK,
        GREEN,
    }
}