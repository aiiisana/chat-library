package advanced.lab.chatlibrary

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object ChatBackend : KoinComponent {
    private val chatService: ChatService by inject()

    suspend fun sendMessage(message: String): String {
        return chatService.sendMessage(message)
    }
}