package pl.paullettuce.android_astarium_interview_app.domain.usecase.synchronize

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.paullettuce.android_astarium_interview_app.domain.extensions.loge
import pl.paullettuce.android_astarium_interview_app.domain.repository.SynchronizationInfoRepository
import pl.paullettuce.android_astarium_interview_app.domain.result.ErrorParser
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.DownloadAndSaveStationsUseCase
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations_keywords.DownloadAndSaveStationsKeywordsUseCase

interface SynchronizeStationsUseCase {
    /**
     * ResultWrapper.Success.data: true if successfully synchronized, false if was already up to date
     */
    operator fun invoke(): Single<ResultWrapper<Boolean>>
}

class SynchronizeStationsUseCaseImpl(
    private val synchronizationRepository: SynchronizationInfoRepository,
    private val downloadAndSaveStationsUseCase: DownloadAndSaveStationsUseCase,
    private val downloadAndSaveStationsKeywordsUseCase: DownloadAndSaveStationsKeywordsUseCase,
) : SynchronizeStationsUseCase {
    override fun invoke(): Single<ResultWrapper<Boolean>> {
        if (!synchronizationRepository.isSynchronizationNeeded()) {
            return Single.just(ResultWrapper.success(false))
        }

        return downloadAndSaveStationsUseCase()
            .flatMap { downloadAndSaveStationsKeywordsUseCase() }
            .doOnSuccess { synchronizationRepository.saveSynchronizationTimestampNow() }
            .onErrorReturn {
                loge(it)
                ResultWrapper.failure(ErrorParser.parseError(it))
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}