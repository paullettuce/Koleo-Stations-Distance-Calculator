package pl.paullettuce.android_astarium_interview_app.domain.distance

import android.graphics.PointF
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object DistanceCalculator {

    /**
     * Calculate distance between two points using Haversine formula
     */
    fun getDistance(p1: PointF, p2: PointF): Double {
        val earthRadius = 6378137 // Earth’s mean radius in meter
        val dLat = radians(p2.x - p1.x)
        val dLong = radians(p2.y - p1.y)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(radians(p1.x)) * cos(radians(p2.x)) *
                sin(dLong / 2) * sin(dLong / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return (earthRadius * c) / 1000// returns the distance in kms
    }

    private fun radians(x: Float): Double {
        return x * Math.PI / 180
    }
}