package pl.paullettuce.android_astarium_interview_app.domain.usecase.synchronize

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.paullettuce.android_astarium_interview_app.domain.extensions.loge
import pl.paullettuce.android_astarium_interview_app.domain.mappers.AddNormalizedNameColumnListMapper
import pl.paullettuce.android_astarium_interview_app.domain.mappers.AddNormalizedNameColumnMapper
import pl.paullettuce.android_astarium_interview_app.domain.repository.SynchronizationInfoRepository
import pl.paullettuce.android_astarium_interview_app.domain.result.ErrorParser
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
    private val saveStationsUseCase: SaveStationsUseCase,
    private val addNormalizedNameColumnListMapper: AddNormalizedNameColumnListMapper
) : SynchronizeStationsUseCase {
    override fun invoke(): Flowable<ResultWrapper<Boolean>> {
        if (!synchronizationRepository.isSynchronizationNeeded()) {
            return Flowable.just(ResultWrapper.success(false))
        }

        return downloadStationsUseCase()
            .map {
                addNormalizedNameColumnListMapper.map(it)
            }
            .flatMap {
                saveStationsUseCase(it)
                    .doOnNext { synchronizationRepository.saveSynchronizationTimestampNow() }
            }
            .onErrorReturn {
                loge(it)
                ResultWrapper.failure(ErrorParser.parseError(it))
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}