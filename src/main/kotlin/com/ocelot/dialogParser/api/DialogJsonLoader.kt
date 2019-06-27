package com.ocelot.dialogParser.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object DialogJsonLoader {

    val gson: Gson = GsonBuilder().registerTypeAdapter(Dialog::class.java, Dialog.Deserializer())
        .registerTypeAdapter(Dialog.Text::class.java, Dialog.Text.Deserializer()).create()

    fun load(stream: InputStream): Dialog {
        BufferedReader(InputStreamReader(stream)).use { reader ->
            val builder = StringBuilder()
            reader.forEachLine { line ->
                builder.append(line)
            }
            return load(builder.toString())
        }
    }

    fun load(json: String): Dialog {
        return gson.fromJson(json, Dialog::class.java)
    }
}