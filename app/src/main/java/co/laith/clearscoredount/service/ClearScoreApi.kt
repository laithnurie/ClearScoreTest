package co.laith.clearscoredount.service

import io.reactivex.Single
import retrofit2.http.GET

interface ClearScoreApi {
    @GET("mockcredit/values")
    fun getCredit(): Single<CreditScoreResponse>
}