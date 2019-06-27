package com.ocelot.dialogParser.api

interface DialogReader {

    fun printMessage(dialog: Dialog, text: Dialog.Text)

    fun printResponse(dialog: Dialog, response: Dialog.Response)
}