package advanced.lab.chatlibrary

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

object ChatBackend : KoinComponent {
    private val chatService: ChatService by inject()

    init {
        startKoinIfNeeded()
    }

    suspend fun sendMessage(message: String): String {
        return chatService.sendMessage(message)
    }

    private fun startKoinIfNeeded() {
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                modules(module {
                    single { provideOkHttpClient() }
                    single { provideChatService(get()) }
                })
            }
        }
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

    private fun provideChatService(client: OkHttpClient): ChatService {
        return ChatServiceImpl(client)
    }
}