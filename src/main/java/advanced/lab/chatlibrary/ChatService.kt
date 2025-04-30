package advanced.lab.chatlibrary

interface ChatService {
    
    suspend fun sendMessage(message: String): String
}
