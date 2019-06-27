import com.ocelot.dialogParser.api.Dialog
import com.ocelot.dialogParser.api.DialogJsonLoader
import com.ocelot.dialogParser.api.DialogReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

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
    val dialog = DialogJsonLoader.load(ClassLoader.getSystemResourceAsStream("test.json"))
    println(dialog.getStartingText().responses)
}

class Test : DialogReader {

    override fun printMessage(dialog: Dialog, text: Dialog.Text) = println(text.message)

    override fun printResponse(dialog: Dialog, response: Dialog.Response) = println(response.message)
}