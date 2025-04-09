package advanced.lab.chatlibrary

import android.content.Context
import android.content.Intent

object ChatLauncher {
    fun start(context: Context) {
        context.startActivity(Intent(context, ChatActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
}