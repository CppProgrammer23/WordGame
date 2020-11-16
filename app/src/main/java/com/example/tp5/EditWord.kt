package com.example.tp5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import classes.WordManager
import kotlinx.android.synthetic.main.activity_edit_word.*
import classes.Word

class EditWord : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_word)
        val bundle:Bundle?=intent.extras
        word.setText(bundle?.getString("word"))
        type.setText(bundle?.getString("type"))
        genre.setText(bundle?.getString("genre"))
        btnValidate.setOnClickListener(){
            val m:String=word.text.toString()
            val t:String=type.text.toString()
            val g:String=genre.text.toString()
            val wordInst=Word(mot=m,taille=m.length,type=t,genre=g)
            val inst=WordManager(applicationContext)
            inst.updateWord(m,wordInst)
        }
    }
}