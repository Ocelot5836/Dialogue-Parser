package com.ocelot.dialogParser.api

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A dialog between an 'NPC' and the user.
 */
class Dialog(private val info: DialogInfo, private val reader: DialogReader) {

    var currentText: DialogInfo.Text = info.getStartingText()
    var done: Boolean = false
    var awaitingInput: Boolean = false

    init {
        GlobalScope.launch {
            printText(currentText)
        }
    }

    private suspend fun printText(text: DialogInfo.Text) {
        delay(text.delay.toLong())
        reader.printMessage(info, text)
        reader.printResponse(info, text.responses)
        currentText = text
        awaitingInput = true
    }

    /**
     * Responds to the current input.
     * @param index The response index to select from the current text
     */
    fun respond(index: Int) {
        if (!awaitingInput && currentText.responses.isNotEmpty())
            return

        awaitingInput = false
        GlobalScope.launch {
            if (currentText.responses.isEmpty()) {
                done = true
            } else {
                if (index >= 0 && index < currentText.responses.size) {
                    val response: DialogInfo.Response = currentText.responses[index]
                    val text = info.getTextByLabel(response.target)
                    if (text == DialogInfo.EMPTY_TEXT) {
                        currentText = DialogInfo.EMPTY_TEXT
                    } else {
                        printText(text)
                    }
                }
            }
        }
    }
}