package com.ocelot.dialogParser.api

/**
 * Used by [Dialogue] to print out messages from a conversation.
 *
 * @author Ocelot
 * @see Dialogue
 * @since 1.0
 */
interface DialogueReader {

    /**
     * Sends a [text] from a [DialogueInfo].
     */
    fun printMessage(dialogue: DialogueInfo, text: DialogueInfo.Text)

    /**
     * Sends a list of [responses] from a [DialogueInfo].
     */
    fun printResponse(dialogue: DialogueInfo, responses: List<DialogueInfo.Response>)
}