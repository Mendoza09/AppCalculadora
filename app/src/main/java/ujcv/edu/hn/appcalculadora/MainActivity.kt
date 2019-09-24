package ujcv.edu.hn.appcalculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateUI("")

    }

    val operationList: MutableList<String> = arrayListOf()
    val numCache: MutableList<String> = arrayListOf()

    private fun extractString (items: List<String>, connect: String = ""): String {
        if (items.isEmpty()) return ""
        return items.reduce { acc, s ->  acc + connect + s}
    }

    private fun updateUI (mainUIString: String) {
        val calculationString = extractString(operationList, "")
        var calculationTxtView = findViewById<View>(R.id.numberDisp) as TextView
        calculationTxtView.text = calculationString

        val ans = findViewById<View>(R.id.display) as TextView
        ans.text = mainUIString
    }

    fun numberSmash (view: View) {
        val button = view as Button
        val numString = button.text

        numCache.add (numString.toString())
        val text = extractString(numCache)
        updateUI(text)
    }

    fun operatorSmash (view: View) {
        val button = view as Button
        if (numCache.isEmpty()) return

        operationList.add(extractString(numCache))
        numCache.clear()
        operationList.add(button.text.toString())
        updateUI(button.text.toString())
    }

    private fun clearCache () {
        operationList.clear()
        numCache.clear()
    }

    fun allClear (view: View) {
        clearCache()
        updateUI("")
    }

    fun equalSmash (view: View) {
        operationList.add (extractString(numCache))
        numCache.clear()

        val calculator = StringCalculator()
        val answer = calculator.calculate(operationList)

        updateUI("=" + answer.toString())
        clearCache()

    }


}

class StringCalculator {
    fun calculate (calculationList: List<String>): Int {
        var currentOp = ""
        var currentNumber = 0

        calculationList.forEach { token ->

            when {
                token.equals("+")
                        || token.equals("-")
                        || token.equals("X")
                        || token.equals("/") -> currentOp = token

                currentOp.equals("-") -> currentNumber -= token.toInt()
                currentOp.equals("/") -> currentNumber /= token.toInt()
                currentOp.equals("X") -> currentNumber *= token.toInt()

                else -> currentNumber += token.toInt()
            }

        }

        return currentNumber
    }

}
