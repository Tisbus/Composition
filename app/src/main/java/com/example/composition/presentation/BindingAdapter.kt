package com.example.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult

interface OnOptionClickListener{
    fun onOptionClick(option : Int)
}

@BindingAdapter("tvNeedRightAnswers")
fun bindTvNeedRightAnswers(textView: TextView, rightAnswers: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.count_right_answers),
        rightAnswers
    )
}

@BindingAdapter("tvYouBill")
fun bindTvYouBill(textView: TextView, bill: Int) {
    textView.text =
        String.format(textView.context.getString(R.string.your_bill), bill)
}

@BindingAdapter("tvRequiredPercentAnswers")
fun bindTvRequiredPercentAnswers(textView: TextView, percentAnswers: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.percent_right_answers),
        percentAnswers
    )
}

@BindingAdapter("tvPercentRightAnswers")
fun bindTvPercentRightAnswers(textView: TextView, gameResult: GameResult) {

    textView.text =
        String.format(
            textView.context.getString(R.string.percent_answers),
            getPercentOfRightAnswers(gameResult)
        )

}

private fun getPercentOfRightAnswers(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestion == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestion.toDouble()) * 100).toInt()
    }
}

@BindingAdapter("imageResult")
fun bindImageResult(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(getImgResource(winner))
}

private fun getImgResource(winner: Boolean): Int {
    val result = if (winner) {
        R.drawable.smile
    } else {
        R.drawable.nosmile
    }
    return result
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, enough: Boolean) {
    textView.setTextColor(getColorByState(textView.context, enough))
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, enough: Boolean) {
    val color = getColorByState(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

private fun getColorByState(context: Context, enough: Boolean): Int {
    val colorResId = if (enough) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("numberText")
fun bindNumberText(textView: TextView, numSum : Int){
    textView.text = numSum.toString()
}
@BindingAdapter("numberLeftText")
fun bindNumberLeftText(textView: TextView, numSum : Int){
    textView.text = numSum.toString()
}
@BindingAdapter("optionClick")
fun bindOptionClickListener(textView: TextView, clickListener : OnOptionClickListener){
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}