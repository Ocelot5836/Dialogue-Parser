package com.ocelot.dialogParser.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Creates DialogInfo from JSON using Gson.
 *
 * @author Ocelot
 * @see DialogInfo
 */
object DialogJsonLoader {

    private val gson: Gson = GsonBuilder().registerTypeAdapter(DialogInfo::class.java, DialogInfo.Deserializer())
        .registerTypeAdapter(DialogInfo.Text::class.java, DialogInfo.Text.Deserializer()).create()

    /**
     * Creates dialog information from an input stream to a JSON file.
     * @param stream The stream to the JSON file
     */
    fun load(stream: InputStream): DialogInfo {
        BufferedReader(InputStreamReader(stream)).use { reader ->
            val builder = StringBuilder()
            reader.forEachLine { line ->
                builder.append(line)
            }
            return load(builder.toString())
        }
    }

    /**
     * Creates dialog information from a JSON string.
     * @param json The json string that will be used to deserialize the dialog.
     */
    fun load(json: String): DialogInfo {
        return gson.fromJson(json, DialogInfo::class.java)
    }
}