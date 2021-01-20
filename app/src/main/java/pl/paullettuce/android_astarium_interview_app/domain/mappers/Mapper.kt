package pl.paullettuce.android_astarium_interview_app.domain.mappers

interface Mapper<I,O> {
    fun map(input: I): O
}

interface ListMapper<I,O>: Mapper<List<I>,List<O>>