import com.ocelot.dialogParser.api.Dialog
import com.ocelot.dialogParser.api.DialogInfo
import com.ocelot.dialogParser.api.DialogJsonLoader
import com.ocelot.dialogParser.api.DialogReader
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
    val dialogInfo = DialogJsonLoader.load(ClassLoader.getSystemResourceAsStream("test.json"))
    val dialog = Dialog(dialogInfo, test)
    val scanner = Scanner(System.`in`)
    while (!dialog.done) {
        if (dialog.currentText.responses.isEmpty()) {
            dialog.respond(0)
        } else if (dialog.awaitingInput && scanner.hasNextInt()) {
            dialog.respond(scanner.nextInt() - 1)
        }
    }
}

class Test : DialogReader {

    override fun printMessage(dialog: DialogInfo, text: DialogInfo.Text) = println(text.message)

    override fun printResponse(dialog: DialogInfo, responses: List<DialogInfo.Response>) {
        for (response in responses) println(response.message)
        println()
    }
}