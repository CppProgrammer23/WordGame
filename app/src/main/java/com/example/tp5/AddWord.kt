package com.example.tp5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import classes.Word
import classes.WordManager
import kotlinx.android.synthetic.main.activity_add_word.*

class AddWord : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        AddDb.setOnClickListener(){
            val instManager=WordManager(applicationContext)
            //val wordInstance =
            //WordManager::openWrite
            instManager.openWrite()
            instManager.addWord(Word(mot=hintWord.text.toString(), taille=hintWord.text.length,type=hintType.text.toString(),
                    genre=hintGenre.text.toString()))
            instManager.closeBD()
        }
    }
}
