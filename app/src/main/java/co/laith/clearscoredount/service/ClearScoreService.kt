package co.laith.clearscoredount.service

import co.laith.clearscoredount.error.ErrorMapper
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.*
import javax.inject.Inject

class ClearScoreService @Inject constructor(private val clearScoreApi: ClearScoreApi) {

    fun getCrediScore(): Single<CreditScoreResponse> {
        return clearScoreApi.getCredit()
                .compose(wrapExceptions())
                .compose(addSchedulers())
    }

    private fun <T> wrapExceptions(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.onErrorResumeNext({ throwable -> Single.error(ErrorMapper.map(throwable)) })
        }
    }

    private fun <T> addSchedulers(): SingleTransformer<T, T> {
        return SingleTransformer { it.subscribeOn(io()).observeOn(AndroidSchedulers.mainThread()) }
    }
}