package co.laith.clearscoredount.main

import co.laith.clearscoredount.service.ClearScoreService
import co.laith.clearscoredount.service.CreditReportInfo
import co.laith.clearscoredount.service.CreditScoreResponse
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var creditScoreService: ClearScoreService

    private lateinit var testObserver: TestObserver<MainUiModel>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mainViewModel = MainViewModel(creditScoreService)
    }

    @Test
    fun itShouldReturnAScore() {
        val score = 0
        val minScoreValue = 100
        val maxScoreValue = 500
        val creditReportInfo = CreditReportInfo(score, minScoreValue, maxScoreValue)
        val creditScoreResponse = CreditScoreResponse(creditReportInfo)
        Mockito.`when`(creditScoreService.getCrediScore()).thenReturn(Single.just(creditScoreResponse))

        testObserver = mainViewModel.getCreditScore().test()

        mainViewModel.getCreditScore()

        testObserver.assertValueCount(1)
                .assertNoErrors()
                .assertValue(MainUiModel(score, minScoreValue, maxScoreValue))

    }

    @Test
    fun itShouldReturnAnError() {
        Mockito.`when`(creditScoreService.getCrediScore()).thenReturn(Single.error(Exception()))

        testObserver = mainViewModel.getCreditScore().test()
        mainViewModel.getCreditScore()
        testObserver.assertError(Exception::class.java)

    }

}