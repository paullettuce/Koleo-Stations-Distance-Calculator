package pl.paullettuce.android_astarium_interview_app.domain.distance

import org.junit.Assert
import org.junit.Test

private const val MAX_DIFFERENCE = 0.5 // km

class DistanceCalculatorTest {
    @Test
    fun testDistanceCalculation() {
        val p1 = Point(0.0f, 0.0f)
        val p2 = Point(1.0f, 1.0f)
        val distance: Double = DistanceCalculator.betweenPoints(p1, p2)

        // Verify the distance calculation
        Assert.assertEquals(157.249, distance, MAX_DIFFERENCE)
    }

    @Test
    fun testDistanceCalculationWithSamePoints() {
        val p1 = Point(0.0f, 0.0f)
        val p2 = Point(0.0f, 0.0f)
        val distance: Double = DistanceCalculator.betweenPoints(p1, p2)

        // Verify that distance between same points is 0
        Assert.assertEquals(0.0, distance, 0.0)
    }

    @Test
    fun testDistanceCalculationWithNegativePoints() {
        val p1 = Point(-1.0f, -1.0f)
        val p2 = Point(1.0f, 1.0f)
        val distance: Double = DistanceCalculator.betweenPoints(p1, p2)

        // Verify the distance calculation
        Assert.assertEquals(314.498, distance, MAX_DIFFERENCE)
    }
}
