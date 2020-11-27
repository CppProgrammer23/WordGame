package com.example.tp5

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import classes.WordBdHelper
import classes.WordManager
import kotlinx.android.synthetic.main.activity_compose.*
import kotlinx.android.synthetic.main.activity_guess.*

class GuessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess)
        val inst= WordManager(applicationContext)
        var score=10
        var nbr_try=5
        inst.openReadBD()
        val str = inst.getRandomNoun()
        inst.createTabScore()
        myHint.setText("The word contain "+str[1]+" at position 2 and its length is: "+str.length)
        inst.insertScore(0)
        if(inst.getScore()!=null)
            bstSc.setText("Best Score: "+inst.getScore())

        inst.closeBD()
        btnGuess.setOnClickListener(){
            inst.openReadBD()
            if(WordToGuess.text.toString()!=str){
                Toast.makeText(applicationContext,"Try again",Toast.LENGTH_SHORT).show()
                nbr_try--
                if(nbr_try==0){
                    textSc.setText("Score: "+score)
                    Toast.makeText(applicationContext,"You Lost",Toast.LENGTH_LONG).show()
                    textTofind.setText("the word was: "+str)
                    Handler().postDelayed({
                        intent = Intent(applicationContext,MainActivity::class.java)
                        startActivity(intent)
                    }, 2500)

                }
            }
            else{
                score*=nbr_try
                textSc.setText("Score: "+score)
                Toast.makeText(applicationContext,"Good Job",Toast.LENGTH_LONG).show()
                inst.insertScore(score)
                Handler().postDelayed({
                    intent = Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)
                }, 2500)

            }
            inst.closeBD()
        }
        btnResSc.setOnClickListener(){
            bstSc.setText("Best Score: 0")
            inst.openWrite()
            inst.dropTab()
            inst.closeBD()
        }
    }
}