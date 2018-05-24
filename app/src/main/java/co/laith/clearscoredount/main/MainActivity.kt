package co.laith.clearscoredount.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.laith.clearscoredount.R
import co.laith.clearscoredount.di.DaggerMainComponent
import co.laith.clearscoredount.donut.DountView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var scoreDount: DountView

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMainComponent.create().inject(this)

        scoreDount = findViewById(R.id.score_dount)

    }

    override fun onResume() {
        super.onResume()
        fetchScore()
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

    private fun fetchScore() {
        disposables.add(viewModel.getCreditScore()
                .subscribe({
                    scoreDount.setScore(it.score, it.minLimit, it.maxLimit)
                }, {
                    scoreDount.showError(it)
                }))
    }
}
