package com.ocelot.dialogParser.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.InputStream

/**
 * Creates [DialogueInfo] from JSON.
 *
 * @author Ocelot
 * @see DialogueInfo
 * @since 1.0
 */
object DialogueJsonLoader {

    private val parser = jacksonObjectMapper()

    /**
     * Creates dialogue information from the [stream] as a string.
     */
    fun load(stream: InputStream): DialogueInfo {
        return parser.readValue(stream, DialogueInfo::class.java)
    }

    /**
     * Creates dialogue information from the [json] string.
     */
    fun load(json: String): DialogueInfo {
        return parser.readValue(json, DialogueInfo::class.java)
    }
}