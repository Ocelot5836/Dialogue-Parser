package com.ocelot.dialogParser.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class Dialog(private val texts: List<Text>) {

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

    class Text(val label: String, val message: String, val responses: List<Response>) {

        class Deserializer : JsonDeserializer<Text> {

            override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Text {
                val textJson = json.asJsonObject

                var label = ""
                var message = ""
                val responses: MutableList<Response> = ArrayList()

                if (textJson.has("label"))
                    label = textJson.get("label").asString
                if (textJson.has("message"))
                    message = textJson.get("message").asString
                if (textJson.has("response")) {
                    val responseJson = textJson.get("response").asJsonArray
                    for (element in responseJson) {
                        responses.add(context.deserialize(element, Dialog.Response::class.java))
                    }
                }

                return Text(label, message, responses)
            }
        }
    }

    class Response(val message: String, val target: String)

    class Deserializer : JsonDeserializer<Dialog> {

        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Dialog {
            val texts = ArrayList<Text>()

            val jsonArray = json.asJsonArray
            for (element in jsonArray) {
                texts.add(context.deserialize(element, Text::class.java))
            }

            return Dialog(texts)
        }
    }

    companion object {
        val EMPTY_TEXT = Text("", "", ArrayList())
    }
}