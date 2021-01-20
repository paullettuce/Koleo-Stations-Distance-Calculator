package pl.paullettuce.android_astarium_interview_app.domain.usecase.autofill_hints

import io.reactivex.rxjava3.core.Single
import pl.paullettuce.android_astarium_interview_app.storage.entity.AutofillHintEntity
import pl.paullettuce.android_astarium_interview_app.domain.repository.AutofillHintsRepository

interface GetAutofillHintsUseCase {
    operator fun invoke(): Single<List<AutofillHintEntity>>
}

class GetAutofillHintsUseCaseImpl(
    private val autofillHintsRepository: AutofillHintsRepository
): GetAutofillHintsUseCase {
    override fun invoke() = autofillHintsRepository.getAutofillHints()
}