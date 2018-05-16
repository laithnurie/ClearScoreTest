package co.laith.clearscoredount.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.laith.clearscoredount.R
import co.laith.clearscoredount.di.DaggerMainComponent
import co.laith.clearscoredount.service.ClearScoreService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var disposable: Disposable

    @Inject
    lateinit var clearScoreService: ClearScoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMainComponent.create().inject(this)

        val viewModel = MainViewModel(clearScoreService)

        disposable = viewModel.getCreditScore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this, "the credit score is : " + it.score, Toast.LENGTH_SHORT).show()

                }, {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                })
    }


    override fun onDestroy() {
        super.onDestroy()
        if (disposable.isDisposed) {
            disposable.dispose()
        }
    }
}
