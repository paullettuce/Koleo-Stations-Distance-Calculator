package pl.paullettuce.android_astarium_interview_app.domain.usecase.synchronize

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.paullettuce.android_astarium_interview_app.domain.repository.SynchronizationInfoRepository
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.DownloadStationsUseCase
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.SaveStationsUseCase

interface SynchronizeStationsUseCase {
    /**
     * ResultWrapper.Success.data: true if successfully synchronized, false if was already up to date
     */
    operator fun invoke(): Flowable<ResultWrapper<Boolean>>
}

class SynchronizeStationsUseCaseImpl(
    private val synchronizationRepository: SynchronizationInfoRepository,
    private val downloadStationsUseCase: DownloadStationsUseCase,
    private val saveStationsUseCase: SaveStationsUseCase
) : SynchronizeStationsUseCase {
    override fun invoke(): Flowable<ResultWrapper<Boolean>> {
        if (!synchronizationRepository.isSynchronizationNeeded()) {
            return Flowable.just(ResultWrapper.success(false))
        }

        return downloadStationsUseCase()
            .flatMap {
                when {
                    it.isSuccess() -> {
                        saveStationsUseCase((it as ResultWrapper.Success).data)
                    }
                    it.isFailure() -> {
                        Flowable.just(it as ResultWrapper.Failure)
                    }
                    else -> {
                        Flowable.just(ResultWrapper.loading())
                    }
                }
            }
            .doOnNext { synchronizationRepository.saveSynchronizationTimestampNow() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}