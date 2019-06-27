package com.ocelot.dialogParser.api

/**
 * Used by Dialog to print out messages from a conversation.
 *
 * @author Ocelot
 * @see Dialog
 * @since 1.0
 */
interface DialogReader {

    /**
     * Sends a message from a Dialog.
     *
     * @param dialog The dialog sending the text
     * @param text The text being sent
     */
    fun printMessage(dialog: DialogInfo, text: DialogInfo.Text)

    /**
     * Sends a list of responses from a Dialog.
     *
     * @param dialog The dialog sending the response choices
     * @param responses The responses available to choose from
     */
    fun printResponse(dialog: DialogInfo, responses: List<DialogInfo.Response>)
}