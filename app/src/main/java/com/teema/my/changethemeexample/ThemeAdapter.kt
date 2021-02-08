package com.teema.my.changethemeexample

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.teema.my.changethemeexample.databinding.RowItemBinding

class ThemeAdapter(
    private val context: Activity,
    resource: Int,
    private val dataSet: Array<String>,
    private val downloadedSet: Array<String>,
    private val onPressDownload: (position: Int) -> Boolean,
    private val onPressUse: (position: Int) -> Boolean
) : ArrayAdapter<String>(context, R.layout.row_item, dataSet) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater

        val binding = RowItemBinding.inflate(inflater)

        binding.themeId.text = dataSet[position]

        if (dataSet[position] in downloadedSet) changeState(binding, State.DOWNLOADED)
        else changeState(binding, State.NOT_DOWNLOADED)

        binding.downloadButton.setOnClickListener {
            changeState(binding, State.DOWNLOADING)
            if (onPressDownload(position)) {
                changeState(binding, State.DOWNLOADED)
            } else {
                changeState(binding, State.NOT_DOWNLOADED)
            }
        }

        binding.useButton.setOnClickListener {
            if (onPressUse(position)) {
                changeState(binding, State.USED)
            }
        }

        return binding.root
    }

    fun changeState(binding: RowItemBinding, state: State) {
        val gone = View.GONE
        val visible = View.VISIBLE
        when (state) {
            State.NOT_DOWNLOADED -> {
                binding.downloadButton.visibility = visible
                binding.downloadingText.visibility = gone
                binding.useButton.visibility = gone
                binding.usedText.visibility = gone
            }
            State.DOWNLOADING -> {
                binding.downloadButton.visibility = gone
                binding.downloadingText.visibility = visible
                binding.useButton.visibility = gone
                binding.usedText.visibility = gone
            }
            State.DOWNLOADED -> {
                binding.downloadButton.visibility = gone
                binding.downloadingText.visibility = gone
                binding.useButton.visibility = visible
                binding.usedText.visibility = gone
            }
            State.USED -> {

                binding.downloadButton.visibility = gone
                binding.downloadingText.visibility = gone
                binding.useButton.visibility = gone
                binding.usedText.visibility = visible
            }
        }
    }

    enum class State {
        NOT_DOWNLOADED,
        DOWNLOADING,
        DOWNLOADED,
        USED
    }
}