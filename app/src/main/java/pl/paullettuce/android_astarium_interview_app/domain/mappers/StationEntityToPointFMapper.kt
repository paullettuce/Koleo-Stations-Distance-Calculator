package pl.paullettuce.android_astarium_interview_app.domain.mappers

import pl.paullettuce.android_astarium_interview_app.domain.distance.Point
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity

class StationEntityToPointMapper : Mapper<StationDataEntity, Point> {
    override fun map(input: StationDataEntity): Point {
        return Point(input.latitude.toFloat(), input.longitude.toFloat())
    }
}