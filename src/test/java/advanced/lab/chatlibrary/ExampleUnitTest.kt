package advanced.lab.chatlibrary

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testWebSocketConnection() {
        val client = OkHttpClient()
        val request = Request.Builder().url("wss://echo.websocket.org").build()
        val webSocketListener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                assertEquals("Hello, WebSocket!", text)
            }
        }
        val webSocket = client.newWebSocket(request, webSocketListener)
        webSocket.send("Hello, WebSocket!")
    }
}