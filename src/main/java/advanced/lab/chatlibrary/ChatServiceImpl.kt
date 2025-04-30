// ChatServiceImpl.kt
package advanced.lab.chatlibrary

import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import kotlin.random.Random

class ChatServiceImpl(private val client: OkHttpClient) : ChatService {
    private val supportResponses = listOf(
        "Thank you for your message. We'll get back to you soon.",
        "Please check our FAQ section for quick answers.",
        "Our support team is currently busy. We appreciate your patience.",
        "Have you tried restarting the application?",
        "This issue has been forwarded to our technical team.",
        "We're sorry for the inconvenience. Let me help you with that.",
        "Please provide more details about your issue.",
        "Your request is being processed. Ticket ID: ${Random.nextInt(1000, 9999)}",
        "This is an automated response. A human agent will contact you shortly.",
        "Thank you for contacting support. How can I assist you today?"
    )

    override suspend fun sendMessage(message: String): String {
        // Имитация задержки сети
        delay(Random.nextLong(500, 1500))
        return supportResponses.random()
    }
}