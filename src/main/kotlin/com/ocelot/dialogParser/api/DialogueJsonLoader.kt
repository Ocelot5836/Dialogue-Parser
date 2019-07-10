package com.ocelot.dialogParser.api

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Creates [DialogueInfo] from JSON using [Gson].
 *
 * @author Ocelot
 * @see DialogueInfo
 * @since 1.0
 */
object DialogueJsonLoader {

    private val gson: Gson = Gson()

    /**
     * Creates dialogue information from the [stream] as a string.
     */
    fun load(stream: InputStream): DialogueInfo {
        BufferedReader(InputStreamReader(stream)).use { reader ->
            val builder = StringBuilder()
            reader.forEachLine { line ->
                builder.append(line)
            }
            return load(builder.toString())
        }
    }

    /**
     * Creates dialogue information from the [json] string.
     */
    fun load(json: String): DialogueInfo {
        return gson.fromJson(json, DialogueInfo::class.java)
    }
}