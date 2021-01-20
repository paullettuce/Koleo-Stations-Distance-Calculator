package pl.paullettuce.android_astarium_interview_app.domain.mappers

import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity

class StationEntityToStationInfoListMapper(
    private val itemMapper: StationEntityToStationInfoMapper
): ListMapper<StationDataEntity, StationInfo> {
    override fun map(input: List<StationDataEntity>): List<StationInfo> {
        return input.map { itemMapper.map(it) }
    }
}

class StationEntityToStationInfoMapper: Mapper<StationDataEntity, StationInfo> {
    override fun map(input: StationDataEntity): StationInfo {
        return StationInfo(
            input.id,
            input.name,
            getInitials(input),
            getRegionInfo(input)
        )
    }

    private fun getInitials(input: StationDataEntity): String {
        val name = input.name
        return if (name.isBlank()) ""
        else "${name[0]}"
    }

    private fun getRegionInfo(input: StationDataEntity): String {
        val components = mutableListOf<String>()
        components.appendIfNotBlank(input.region)
        components.appendIfNotBlank(input.country)
        return components.joinToString(separator = ", ")
    }

    private fun MutableList<String>.appendIfNotBlank(text: String) {
        if (text.isNotBlank()) add(text)
    }
}