package co.laith.clearscoredount.donut

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import co.laith.clearscoredount.R

class DountView : FrameLayout {

    private lateinit var scoreText: TextView
    private lateinit var scoreProgress: ProgressBar

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        inflate(context, R.layout.dount_view, this)
        scoreText = findViewById(R.id.score_txt)
        scoreProgress = findViewById(R.id.score_progress)
    }

    fun setScore(score: Int, minScore: Int, maxScore: Int) {
        scoreProgress.max = maxScore
        val scoreAnimation = ValueAnimator.ofInt(minScore, score)
        scoreAnimation.duration = 1500
        scoreAnimation.addUpdateListener({
            val scoreAnimateValue: Int = it.animatedValue as Int
            scoreText.text = scoreAnimateValue.toString()
            scoreProgress.progress = scoreAnimateValue
        })

        scoreAnimation.interpolator = AccelerateDecelerateInterpolator()
        scoreAnimation.start()
    }

    fun showError() {
        scoreText.text = "?"
        scoreProgress.progress = 0

    }
}