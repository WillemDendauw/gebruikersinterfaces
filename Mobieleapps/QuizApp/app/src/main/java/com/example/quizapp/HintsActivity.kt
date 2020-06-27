package com.example.quizapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.example.quizapp.databinding.ActivityHintsBinding
import com.example.quizapp.model.QuizMaster

class HintsActivity : AppCompatActivity() {

    val NO_HINT: Int = 111
    val TEXT_HINT: Int = 222
    val IMAGE_HINT: Int = 333

    val hintVisibility = ObservableField<Int>()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hints)

        val binding =
            DataBindingUtil.setContentView<ActivityHintsBinding>(this, R.layout.activity_hints)
        binding.quizMaster = QuizMaster
        binding.handler = this

        hintVisibility.set(NO_HINT)

        // Met onderstaande code verschijnt er een '<-' (pijltje terug) in de blauwe balk bovenaan het scherm
        // zorg dan wel dat deze activiteit z'n ouderactiviteit kent
        // dat stel je in in AndroidManifest.xml
        // Override dan ook de methode onOptionsItemSelected(item: MenuItem).
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // deze functie wordt opgeroepen als een menu-item geselecteerd wordt
    // als dit menu-item de home-knop is, dan wordt de huidige applicatie (netjes!) afgesloten
    // zodat het resultaat ook teruggegeven wordt aan de oproepende (hoofd-)activiteit
    override fun onOptionsItemSelected(item: MenuItem) : Boolean{
        if(android.R.id.home == item.getItemId()) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        //+/ Omdat de onderstaande constanten ook BUITEN de klasse gekend moeten zijn,
        //+/ staan ze in het companion object.
        //+/ De naam van de constanten is vrij gekozen (en hier belachelijk lang);
        //+/ de inhoud van de constanten is ook vrij gekozen (en redelijk overdreven),
        //+/ maar vooral zo opgesteld dat dit nergens anders voorkomt.
        //+/ Je wil immers niet dat er per ongeluk verwarring ontstaat tussen de verschillende
        //+/ stukken extra informatie die verschillende activities kunnen teruggeven aan de (hoofd-)
        //+/ activiteit.
        //+/ In de praktijk gebruik je de naam van de packages ("com.example.xxx.text_hint_viewed")
        val EXTRA_INFO_OVER_TEXT_HINT_VIEWED =
            "dit.is.een.naam.die.nergens.anders.voorkomt.text_hint_viewed"
        val EXTRA_INFO_OVER_IMAGE_HINT_VIEWED =
            "dit.is.een.naam.die.nergens.anders.voorkomt.image_hint_viewed"

        fun createIntent(ctx: Context): Intent {
            return Intent(ctx, HintsActivity::class.java)
        }
    }

    fun onShowTextHint(view: View) {
        hintVisibility.set(TEXT_HINT)

        //+/ 'intent' is een eigenschap (getIntent) overgeërfd van Activity
        //+/ https://developer.android.com/reference/androidx/appcompat/app/AppCompatActivity#inherited-methods
        val intentMetExtra = intent

        intentMetExtra.putExtra(EXTRA_INFO_OVER_TEXT_HINT_VIEWED, true)
        //+/ Je kiest hier pas bij het instellen van de tweede parameter als welk type die extra info
        //+/ opgeslagen wordt; bij het weer opvragen zal je daar wel rekening mee moeten houden.
        //+/ Omdat we hier duidelijk een boolean hebben opgeslagen, zullen we bij het opvragen van de
        //+/ data die bij deze intent horen, deze code moeten gebruiken:
        //+/     intent.getBooleanExtra(EXTRA_INFO_OVER_TEXT_HINT_VIEWED,false)
        //+/             (2e param is defaultValue -> als EXTRA_INFO nie// t beschikbaar is, dan bevat de info eigenlijk 'false')
        //+/ Dit gebeurt ook effectief in MainActivity.kt, op regel (+-) 49-52:
        //+/     if (data.getBooleanExtra(
        //+/         HintsActivity.EXTRA_INFO_OVER_TEXT_HINT_VIEWED,
        //+/         false
        //+/     )
        //+/ Merk op dat 'data' in de code hierboven nu de naam is van een object van type Intent.
        //+/ (OK, dat had beter gekunnen... de naamkeuze is hier verwarrend!)

        //+/ Indien we echter bvb. een String zouden ingesteld hebben als extra informatie,
        //+/ dus met de code
        //+/     intentMetExtra.putExtra(EXTRA_INFO_OVER_TEXT_HINT_VIEWED, "dit is extra info")
        //+/ dan zal deze data worden opgevraagd met de methode 'getStringExtra' ipv 'getBooleanExtra':
        //+/     intent.getStringExtra(EXTRA_INFO_OVER_TEXT_HINT_VIEWED,"geen info gevonden")
        //+/ (waarbij de tweede parameter weerom een default-antwoord is, voor als er geen extra informatie
        //+/ ingesteld werd).

        setResult(RESULT_OK,intentMetExtra)
        //+/ Dus niet alleen zeggen of result 'ok' is, maar ook de extra's meegeven!
    }

    fun onShowImageHint(view: View) {
        hintVisibility.set(IMAGE_HINT)
        val intentMetExtra = intent
        intentMetExtra.putExtra(EXTRA_INFO_OVER_IMAGE_HINT_VIEWED, true)
        setResult(RESULT_OK,intentMetExtra)
        //+/ Dus niet alleen zeggen of result 'ok' is, maar ook de extra's meegeven!
    }
}