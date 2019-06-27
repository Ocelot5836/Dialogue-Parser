package com.ocelot.dialogParser.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

/**
 * All information related to a Dialog. Can be created from JSON in the DialogJsonLoader.
 *
 * @author Ocelot
 * @see Dialog
 * @see DialogJsonLoader
 */
class DialogInfo(private val texts: List<Text>) {

    fun getStartingText(): Text {
        if (getTextByLabel("start") != EMPTY_TEXT)
            return getTextByLabel("start")
        if (texts.isNotEmpty())
            return texts[0]
        return EMPTY_TEXT
    }

    fun getTextByLabel(label: String): Text {
        if (label.isEmpty())
            return EMPTY_TEXT

        for (text in texts) {
            if (text.label == label)
                return text
        }
        return EMPTY_TEXT
    }

    class Text(val label: String, val message: String, val delay: Number, val responses: List<Response>) {

        class Deserializer : JsonDeserializer<Text> {

            override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Text {
                val textJson = json.asJsonObject

                var label = ""
                var message = ""
                var delay: Number = 0
                val responses: MutableList<Response> = ArrayList()

                if (textJson.has("label"))
                    label = textJson.get("label").asString
                if (textJson.has("message"))
                    message = textJson.get("message").asString
                if (textJson.has("delay"))
                    delay = textJson.get("delay").asNumber
                if (textJson.has("response")) {
                    val responseJson = textJson.get("response").asJsonArray
                    for (element in responseJson) {
                        responses.add(context.deserialize(element, DialogInfo.Response::class.java))
                    }
                }

                return Text(label, message, delay, responses)
            }
        }
    }

    class Response(val message: String, val target: String)

    class Deserializer : JsonDeserializer<DialogInfo> {

        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DialogInfo {
            val texts = ArrayList<Text>()

            val jsonArray = json.asJsonArray
            for (element in jsonArray) {
                texts.add(context.deserialize(element, Text::class.java))
            }

            return DialogInfo(texts)
        }
    }

    companion object {
        val EMPTY_TEXT = Text("", "", 0, ArrayList())
    }
}