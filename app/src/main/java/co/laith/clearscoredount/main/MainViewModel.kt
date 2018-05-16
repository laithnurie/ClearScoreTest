package co.laith.clearscoredount.main

import co.laith.clearscoredount.service.ClearScoreService
import io.reactivex.Single

class MainViewModel(private val clearScoreService: ClearScoreService) {

    fun getCreditScore(): Single<MainUiModel> {
        return clearScoreService.getCrediScore().map {
            MainUiModel(it.creditReportInfo.score,
                    it.creditReportInfo.minScoreValue,
                    it.creditReportInfo.maxScoreValue)
        }
    }
}