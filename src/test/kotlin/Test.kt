import com.ocelot.dialogParser.api.Dialogue
import com.ocelot.dialogParser.api.DialogueInfo
import com.ocelot.dialogParser.api.DialogueJsonLoader
import com.ocelot.dialogParser.api.DialogueReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.*

fun main() {
    val deferred = (1..100).map { n ->
        GlobalScope.async {
            n
        }
    }



    runBlocking {
        val sum = deferred.sumBy { it.await() }
//        println("Sum: $sum")
    }

    val test = Test()
    val input = Test::class.java.getResourceAsStream("/test.json")
    val dialogInfo = DialogueJsonLoader.load(input)
    val dialog = Dialogue(dialogInfo, test)
    val scanner = Scanner(System.`in`)
    while (!dialog.done) {
        if (dialog.currentText.responses.isEmpty()) {
            dialog.respond(0)
        } else if (dialog.awaitingInput && scanner.hasNextInt()) {
            dialog.respond(scanner.nextInt() - 1)
        }
    }
}

class Test : DialogueReader {

    override fun printMessage(dialogue: DialogueInfo, text: DialogueInfo.Text) = println(text.message)

    override fun printResponse(dialogue: DialogueInfo, responses: List<DialogueInfo.Response>) {
        for (response in responses) println(response.message)
        println()
    }
}