package advanced.lab.chatlibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import advanced.lab.chatlibrary.databinding.ActivityChatBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MessageAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.addMessage(Message(
            "Hello! Thank you for contacting support. How can I help you today?",
            false
        ))

        binding.sendButton.setOnClickListener {
            val msg = binding.messageEditText.text.toString()
            if (msg.isNotEmpty()) {
                adapter.addMessage(Message(msg, true))
                sendMessageToBackend(msg)
                binding.messageEditText.text.clear()
            }
        }
    }

    private fun sendMessageToBackend(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ChatBackend.sendMessage(message)
                withContext(Dispatchers.Main) {
                    adapter.addMessage(Message(response, false))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    adapter.addMessage(Message("Error: ${e.message}", false))
                }
            }
        }
    }
}