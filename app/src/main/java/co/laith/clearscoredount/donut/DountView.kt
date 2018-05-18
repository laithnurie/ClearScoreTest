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

    companion object {
        private const val ANIM_DURATION = 1500L
    }

    private lateinit var scoreText: TextView
    private lateinit var outOfMaxText: TextView
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
        outOfMaxText = findViewById(R.id.out_of_max_txt)
        scoreProgress = findViewById(R.id.score_progress)
    }

    fun setScore(score: Int, minScore: Int, maxScore: Int) {
        scoreProgress.max = maxScore
        val scoreAnimation = ValueAnimator.ofInt(minScore, score)
        scoreAnimation.duration = ANIM_DURATION
        scoreAnimation.addUpdateListener({
            val scoreAnimateValue: Int = it.animatedValue as Int
            scoreText.text = scoreAnimateValue.toString()
            scoreProgress.progress = scoreAnimateValue
        })

        scoreAnimation.interpolator = AccelerateDecelerateInterpolator()
        scoreAnimation.start()

        outOfMaxText.text = resources.getString(R.string.out_of_max, maxScore)
    }

    fun showError() {
        scoreProgress.progress = 0
        scoreText.text = context.getString(R.string.score_error)
        outOfMaxText.text = context.getString(R.string.something_went_wrong)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = measuredWidth
        val height = measuredHeight

        val size = if (width > height) {
            height
        } else {
            width
        }
        val progressBarLayoutParams = scoreProgress.layoutParams
        progressBarLayoutParams.height = size
        progressBarLayoutParams.width = size

        scoreProgress.layoutParams = progressBarLayoutParams
        setMeasuredDimension(size, size)
    }
}