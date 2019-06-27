package com.ocelot.dialogParser.api

interface DialogReader {

    fun printMessage(dialog: DialogInfo, text: DialogInfo.Text)

    fun printResponse(dialog: DialogInfo, responses: List<DialogInfo.Response>)
}