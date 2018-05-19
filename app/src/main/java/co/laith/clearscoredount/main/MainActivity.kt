package co.laith.clearscoredount.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.laith.clearscoredount.R
import co.laith.clearscoredount.di.DaggerMainComponent
import co.laith.clearscoredount.donut.DountView
import co.laith.clearscoredount.service.ClearScoreService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var scoreDount: DountView
    private lateinit var viewModel: MainViewModel

    private var disposable: Disposable? = null

    @Inject
    lateinit var clearScoreService: ClearScoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMainComponent.create().inject(this)

        scoreDount = findViewById(R.id.score_dount)

        viewModel = MainViewModel(clearScoreService)
    }

    override fun onResume() {
        super.onResume()
        fetchScore()
    }

    override fun onPause() {
        super.onPause()
        if (!disposable?.isDisposed!!) {
            disposable?.dispose()
        }
    }

    private fun fetchScore() {
        if (disposable != null && !disposable?.isDisposed!!) {
            disposable?.dispose()
        }
        disposable = viewModel.getCreditScore()
                .subscribe({
                    scoreDount.setScore(it.score, it.minLimit, it.maxLimit)
                }, {
                    scoreDount.showError(it)
                })
    }
}
