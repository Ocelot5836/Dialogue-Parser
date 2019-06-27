package com.ocelot.dialogParser.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object DialogJsonLoader {

    val gson: Gson = GsonBuilder().registerTypeAdapter(DialogInfo::class.java, DialogInfo.Deserializer())
        .registerTypeAdapter(DialogInfo.Text::class.java, DialogInfo.Text.Deserializer()).create()

    fun load(stream: InputStream): DialogInfo {
        BufferedReader(InputStreamReader(stream)).use { reader ->
            val builder = StringBuilder()
            reader.forEachLine { line ->
                builder.append(line)
            }
            return load(builder.toString())
        }
    }

    fun load(json: String): DialogInfo {
        return gson.fromJson(json, DialogInfo::class.java)
    }
}