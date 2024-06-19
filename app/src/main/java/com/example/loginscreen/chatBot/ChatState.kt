package com.example.loginscreen.chatBot

import android.graphics.Bitmap
import com.example.loginscreen.chatBot.data.Chat

data class ChatState (
    val chatList: MutableList<Chat> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null
)