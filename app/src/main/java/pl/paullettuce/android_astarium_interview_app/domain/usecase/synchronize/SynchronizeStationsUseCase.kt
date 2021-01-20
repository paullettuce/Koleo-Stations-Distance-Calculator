package pl.paullettuce.android_astarium_interview_app.domain.usecase.synchronize

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.DownloadStationsUseCase
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.SaveStationsUseCase
import pl.paullettuce.android_astarium_interview_app.storage.preferences.Preferences

interface SynchronizeStationsUseCase {
    /**
     * ResultWrapper.Success.data: true if successfully synchronized, false if was already up to date
     */
    operator fun invoke(): Flowable<ResultWrapper<Boolean>>
}

class SynchronizeStationsUseCaseImpl(
    private val preferences: Preferences,
    private val downloadStationsUseCase: DownloadStationsUseCase,
    private val saveStationsUseCase: SaveStationsUseCase
) : SynchronizeStationsUseCase {
    override fun invoke(): Flowable<ResultWrapper<Boolean>> {
        if (!needsSynchronization(preferences)) return Flowable.just(ResultWrapper.success(false))

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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun needsSynchronization(preferences: Preferences): Boolean {
        return false
    }
}