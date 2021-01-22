package pl.paullettuce.android_astarium_interview_app.domain.mappers

import org.apache.commons.lang3.StringUtils
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity

class AddNormalizedNameColumnListMapper(
    private val itemMapper: AddNormalizedNameColumnMapper
) : ListMapper<StationDataEntity, StationDataEntity> {
    override fun map(input: List<StationDataEntity>): List<StationDataEntity> {
        return input.map { itemMapper.map(it) }
    }
}

class AddNormalizedNameColumnMapper : Mapper<StationDataEntity, StationDataEntity> {
    override fun map(input: StationDataEntity): StationDataEntity {
        return input.createNormalizedName()
    }

    private fun StationDataEntity.createNormalizedName(): StationDataEntity {
        normalizedName = StringUtils.stripAccents(name)
        return this
    }
}