package advanced.lab.chatlibrary

import advanced.lab.chatlibrary.ChatService
import advanced.lab.chatlibrary.ChatServiceImpl
import advanced.lab.chatlibrary.ErrorHandlingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val chatLibraryModule = module {
    single { provideOkHttpClient() }
    single<ChatService> { ChatServiceImpl(get()) }
}

private fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(ErrorHandlingInterceptor())
        .build()
}