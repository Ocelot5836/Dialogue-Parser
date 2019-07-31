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
    val dialogInfo = DialogueJsonLoader.load("[\n" +
            "\t{\n" +
            "\t\t\"label\": \"start\",\n" +
            "\t\t\"message\": \"Noble Heirarch. What would you have your Arbiter do?\",\n" +
            "\t\t\"response\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"message\": \"What can you tell me about yourself?\",\n" +
            "\t\t\t\t\"target\": \"arbiterlore1\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"message\": \"That is all. The Great Journey awaits.\",\n" +
            "\t\t\t\t\"target\": \"\"\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"arbiterlore1\",\n" +
            "\t\t\"message\": \"Your predecessor, the Noble Prophet of Truth, named me Arbiter to defeat the heretic faction and as punishment for my mistake\",\n" +
            "\t\t\"response\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"message\": \"And what of these heretics?\",\n" +
            "\t\t\t\t\"target\": \"hereticslore\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"message\": \"What was your mistake?\",\n" +
            "\t\t\t\t\"target\": \"arbiter2\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"message\": \"I have another task for you\",\n" +
            "\t\t\t\t\"target\": \"start\"\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"hereticslore\",\n" +
            "\t\t\"message\": \"The heretic leader known as Sesa 'Refumee abducted the Oracle and was spreading lies amongst the Convenant. I killed the heretic and Tartarus delivered the Holy Oracle to the Heirarchs\",\n" +
            "\t\t\"response\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"message\": \"Tell me about this Oracle\",\n" +
            "\t\t\t\t\"target\": \"oracle\"\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"oracle\",\n" +
            "\t\t\"message\": \"The Holy Oracle guides us to salvation! With their help we shall commence The Great Journey!\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"arbiter2\",\n" +
            "\t\t\"message\": \"After the glassing of the human world, I followed their fleeing ship with all the ships under my command. They desecrated the Holy Ring with their filfthy footprints but, with the release of the parasite, I was unable to safeguard Halo from the Demon\",\n" +
            "\t\t\"response\": [\n" +
            "\t\t{\n" +
            "\t\t\t\t\"message\": \"Tell me about the Holy Ring\",\n" +
            "\t\t\t\t\"target\": \"halo\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"message\": \"The parasite?\",\n" +
            "\t\t\t\t\"target\": \"flood\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"message\": \"Who is this 'Demon'?\",\n" +
            "\t\t\t\t\"target\": \"chief\"\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"halo\",\n" +
            "\t\t\"message\": \"Halo. One of the seven constructed by the Forerunners. Millenia ago they too embarked on the Great Journey and ascended through the activation of the rings. We wish to follow in their footsteps.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"flood\",\n" +
            "\t\t\"message\": \"The Flood attacked us as we hunted down the remaining humans. We diverted our attention to eradicating the parasite, but it spread far beyond our control.\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"label\": \"chief\",\n" +
            "\t\t\"message\": \"The human's Demon, they call it 'Master Chief', destroyed Halo as the parasite attacked.\"\n" +
            "\t}\n" +
            "]")
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