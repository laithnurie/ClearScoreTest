package co.laith.clearscoredount.donut

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import co.laith.clearscoredount.R
import co.laith.clearscoredount.error.CSException
import co.laith.clearscoredount.error.CSException.ErrorType

class DountView : FrameLayout {

    companion object {
        private const val ANIM_DURATION = 1500L
    }

    private lateinit var scoreText: TextView
    private lateinit var topMessage: TextView
    private lateinit var bottomMessage: TextView
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
        topMessage = findViewById(R.id.top_message_txt)
        bottomMessage = findViewById(R.id.bottom_message_txt)
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

        topMessage.text = resources.getString(R.string.your_credit_score_is)
        bottomMessage.text = resources.getString(R.string.out_of_max, maxScore)
    }

    fun showError(throwable: Throwable) {
        scoreProgress.progress = 0
        var messageResId = R.string.error_unknown
        if (throwable is CSException) {
            messageResId = throwable.messageResId
            if (throwable.errorType == ErrorType.SERVER) {
                Toast.makeText(context, "Check your Connection", Toast.LENGTH_SHORT).show()
            }
        }

        scoreText.text = resources.getString(R.string.score_error)

        topMessage.text = resources.getString(R.string.oh_no)
        bottomMessage.text = resources.getString(messageResId)
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