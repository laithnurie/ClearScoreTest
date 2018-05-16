package co.laith.clearscoredount.main

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class MainUiModel(
        val score: Int,
        val minLimit: Int,
        val maxLimit: Int
) : PaperParcelable {
    companion object {
        @JvmField
        val CREATOR = PaperParcelMainUiModel.CREATOR
    }
}