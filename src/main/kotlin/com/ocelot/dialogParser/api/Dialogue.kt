package com.ocelot.dialogParser.api

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * A dialogue between an 'NPC' and the user.
 *
 * @author Ocelot
 * @see DialogueInfo
 * @see DialogueReader
 * @since 1.0
 */
class Dialogue(private val info: DialogueInfo, private val reader: DialogueReader) {

    var currentText: DialogueInfo.Text = info.getStartingText()
    var done: Boolean = false
    var awaitingInput: Boolean = false

    init {
        GlobalScope.launch {
            printText(currentText)
        }
    }

    private suspend fun printText(text: DialogueInfo.Text) {
        delay(text.delay.toLong())
        reader.printMessage(info, text)
        reader.printResponse(info, text.responses)
        currentText = text
        awaitingInput = true
    }

    /**
     * Selects the option by [index] and sends the option's info or ends the conversation if there are no responses
     */
    fun respond(index: Int) {
        if (!awaitingInput && currentText.responses.isNotEmpty())
            return

        awaitingInput = false
        runBlocking {
            if (currentText.responses.isEmpty()) {
                done = true
            } else {
                if (index >= 0 && index < currentText.responses.size) {
                    val response: DialogueInfo.Response = currentText.responses[index]
                    val text = info.getTextByLabel(response.target)
                    if (text == DialogueInfo.EMPTY_TEXT) {
                        currentText = DialogueInfo.EMPTY_TEXT
                    } else {
                        printText(text)
                    }
                } else {
                    awaitingInput = true
                }
            }
        }
    }
}