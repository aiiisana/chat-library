package advanced.lab.chatlibrary

import android.content.Context
import android.content.Intent

object ChatLibrary {
    fun start(context: Context) {
        val intent = Intent(context, advanced.lab.chatlibrary.ChatActivity::class.java)
        context.startActivity(intent)
    }
}