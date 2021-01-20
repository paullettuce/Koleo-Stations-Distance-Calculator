package pl.paullettuce.android_astarium_interview_app.domain.mappers

import android.graphics.PointF
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity

class StationEntityToPointFMapper: Mapper<StationDataEntity, PointF> {
    override fun map(input: StationDataEntity): PointF {
        return PointF(input.latitude.toFloat(), input.longitude.toFloat())
    }
}