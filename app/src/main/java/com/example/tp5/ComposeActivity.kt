package com.example.tp5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import classes.WordManager
import kotlinx.android.synthetic.main.activity_compose.*

class ComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)
        btnCompose.setOnClickListener(){
            val inst= WordManager(applicationContext)
            inst.openReadBD()
            TextPhrase.setText("The "+inst.getRandomNoun()+" is "+inst.getRandomAdj())
            inst.closeBD()
        }
    }
}