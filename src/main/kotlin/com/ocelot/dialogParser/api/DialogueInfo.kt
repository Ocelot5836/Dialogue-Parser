package com.ocelot.dialogParser.api

/**
 * All information related to a [Dialogue]. Can be created from JSON in [DialogueJsonLoader].
 *
 * @author Ocelot
 * @see Dialogue
 * @see DialogueJsonLoader
 * @since 1.0
 */
class DialogueInfo(private val texts: List<Text>) {

    /**
     * Gets the first line of text.
     */
    fun getStartingText(): Text {
        if (getTextByLabel("start") != EMPTY_TEXT)
            return getTextByLabel("start")
        if (texts.isNotEmpty())
            return texts[0]
        return EMPTY_TEXT
    }

    /**
     * Gets the text specified by a [label] in the JSON.
     */
    fun getTextByLabel(label: String): Text {
        if (label.isEmpty())
            return EMPTY_TEXT

        for (text in texts) {
            if (text.label == label)
                return text
        }
        return EMPTY_TEXT
    }

    /**
     * A single line of text that can be sent by a [Dialogue].
     */
    class Text(val label: String, val message: String, val delay: Number, val responses: List<Response>)

    /**
     * A single response that will send another text if the [target] is valid. It displays [message] when selecting.
     */
    class Response(val message: String, val target: String)

    companion object {
        /**
         * An empty line of text that has no label, message, delay, or responses. Will end a conversation if it is processed by [Dialogue].
         */
        val EMPTY_TEXT = Text("", "", 0, ArrayList())
    }
}