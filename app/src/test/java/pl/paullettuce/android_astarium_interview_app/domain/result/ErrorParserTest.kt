package pl.paullettuce.android_astarium_interview_app.domain.result

import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import java.net.UnknownHostException

class ErrorParserTest {

    @Test
    fun testParseHttpException() {
        val httpException =
            HttpException(retrofit2.Response.error<Any>(426, okhttp3.ResponseBody.create(null, "")))
        val parsedError = ErrorParser.parseError(httpException)

        assertEquals(ParsedError.MissingHeader, parsedError)
    }

    @Test
    fun testParseUnknownHostException() {
        val unknownHostException = UnknownHostException()
        val parsedError = ErrorParser.parseError(unknownHostException)

        assertEquals(ParsedError.NoConnection, parsedError)
    }

    @Test
    fun testParseUnknownException() {
        val exception = Exception("Test exception")
        val parsedError = ErrorParser.parseError(exception)

        assertEquals(ParsedError.UnknownException(exception), parsedError)
    }

    @Test
    fun testParseUnknownHttpCode() {
        val httpException =
            HttpException(retrofit2.Response.error<Any>(411, okhttp3.ResponseBody.create(null, "")))
        val parsedError = ErrorParser.parseError(httpException)

        assertEquals(ParsedError.UnknownHttpCode(411), parsedError)
    }
}
