package advanced.lab.chatlibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import advanced.lab.chatlibrary.databinding.ActivityChatBinding
import okhttp3.*
import okio.ByteString
import java.util.*

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: MessageAdapter
    private var webSocket: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MessageAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.sendButton.setOnClickListener {
            val msg = binding.messageEditText.text.toString()
            if (msg.isNotEmpty()) {
                adapter.addMessage(Message(msg, true))
                webSocket?.send(msg)
                binding.messageEditText.text.clear()
            }
        }

        val client = OkHttpClient()
        val request = Request.Builder().url("wss://echo.websocket.org").build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                runOnUiThread {
                    val displayText = if (text == "203 = 0xcb") "⚠️ Special Message" else text
                    adapter.addMessage(Message(displayText, false))
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                onMessage(webSocket, bytes.utf8())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocket?.close(1000, null)
    }
}