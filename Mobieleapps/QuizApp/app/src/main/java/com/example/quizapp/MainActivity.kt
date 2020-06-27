package com.example.quizapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.model.QuizMaster

class MainActivity : AppCompatActivity() {

    val quizMaster = QuizMaster
    val HINT_REQUEST = 22 // je zou een ander getal kunnen kiezen
    // deze code gebruik je om mee te geven als je een activiteit opstart
    // dus deze code zal je later toestaan om te weten welke opgestarte activiteit terugkeerd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.quizMaster = quizMaster
        binding.main = this
    }

    // deze methode wordt opgeroepen als je in de huidige (hoofd-)activiteit zit,
    // en het resultaat van een andere (neve-)activiteit binnenkomt
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // het Intent-object ('data') bevat informatie van die andere acitiviteit
        // de resultCode zegt of die andere activiteit 'normaal' afgesloten is of niet
        // de requestcode is de code die deze (hoofd-)activiteit meegaf toen-ie die andere activiteit opstartte
        // zo kan de (hoofd-)activiteit nu informatie van de juiste (neven-)activiteit opvragen

        if(requestCode == HINT_REQUEST){
            var boodschap = ""
            // als de activiteit netjes teruggekeerd is
            if(resultCode == Activity.RESULT_OK){
                if(data != null){
                    // nu kan je de data opvragen die bij die andere (neve-)activiteit hoort
                    if(data.getBooleanExtra(
                            HintsActivity.EXTRA_INFO_OVER_TEXT_HINT_VIEWED,
                            false
                        )
                    ) {
                        boodschap += "you read the textual hint\n"
                    }
                } else {
                    boodschap += "no hint used" //deze is niet aan de orde
                }
            }
            else {
                boodschap += "got no hint!"
            }
            Toast.makeText(application, boodschap, Toast.LENGTH_SHORT).show()
        }
    }

    // met method reference in activity_main.xml (dus @{main.handleClickHint});
    // dus hier moet de parameterlijst(view: View) zijn
    fun handleClickHint(view: View){
        //Toast.makeText(applicationContext, QuizMaster.currentQuestion.get()?.textHint, Toast.LENGTH_SHORT).show()
        val intent = HintsActivity.createIntent(this)
        startActivityForResult(intent, HINT_REQUEST)
        // hierboven het je een andere activiteit gestart, vanuit deze (hoofd-)activiteit.
        // je wil later echter een resultaat opvangen.
        // dan moet je wel weten welke opgeroepen activiteit jou later antwoord geeft.
        // dus je geeft een code mee met de opstart van die andere activiteit.
        // (Hier heeft die code de waarde 22, dat is puur willekeurig gekozen. het had ook gewoon 1 kunnen zijn
    }

    // met listener binding method in activity_main.xml (dus #{() -> main.handleClickOK()})
    // hier moet de parameterlijst overeenkomen met wat wordt opgeroepen in activity_main.xml
    fun handleClickOK(){
        if(quizMaster.validateAnswer()){
            Toast.makeText(applicationContext, "answer is OK", Toast.LENGTH_SHORT).show()
            quizMaster.nextQuestion()
        }
        else {
            Toast.makeText(applicationContext, "wrong answer, try again", Toast.LENGTH_SHORT).show()
        }
        quizMaster.userAnswer.set("")
    }
}