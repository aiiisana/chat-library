package advanced.lab.chatlibrary

import okhttp3.Interceptor
import okhttp3.Response

class ErrorHandlingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            throw when (response.code) {
                401 -> UnauthorizedException()
                404 -> NotFoundException()
                500 -> ServerException()
                else -> ApiException("HTTP ${response.code}")
            }
        }
        return response
    }
}