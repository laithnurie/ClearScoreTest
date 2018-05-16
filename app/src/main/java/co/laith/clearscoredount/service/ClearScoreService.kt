package co.laith.clearscoredount.service

import io.reactivex.Single
import javax.inject.Inject

class ClearScoreService @Inject constructor(private val clearScoreApi: ClearScoreApi) {

    fun getCrediScore(): Single<CreditScoreResponse> {
        return clearScoreApi.getCredit()
    }
}