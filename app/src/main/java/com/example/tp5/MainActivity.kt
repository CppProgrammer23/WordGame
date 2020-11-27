package com.example.tp5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import classes.WordManager
import classes.Word
import kotlinx.android.synthetic.main.activity_edit_word.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var objet:View?=null
    private lateinit var wr:String
    private lateinit var ty:String
    private lateinit var ge:String
    private lateinit var word:Word
    var pos: Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAdd.setOnClickListener(){
            intent = Intent(applicationContext,AddWord::class.java)
            startActivity(intent)
        }
        btncmp.setOnClickListener(){
            intent = Intent(applicationContext,ComposeActivity::class.java)
            startActivity(intent)
        }
        btnGame.setOnClickListener(){
            intent = Intent(applicationContext,GuessActivity::class.java)
            startActivity(intent)
        }

        wordslist.setOnItemClickListener { parent, view, position, id ->
            pos=position
        }
        registerForContextMenu(wordslist)
        btnRef.setOnClickListener(){
            refreshWord()
        }

    }
    private lateinit var list:List<Word>
    private fun refreshWord(){
        val ins=WordManager(applicationContext)
        ins.openReadBD()
        //update the ListView
        list=ins.getWords()
        wordslist.adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        ins.closeBD()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu?.add(Menu.NONE,1,Menu.NONE,"Edit")
        menu?.add(Menu.NONE,2,Menu.NONE,"Delete")
        objet =v
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            1 -> {
                intent=Intent(applicationContext,EditWord::class.java)
                word = list[pos]
                intent.putExtra("word",word.mot)
                intent.putExtra("type",word.type)
                intent.putExtra("genre",word.genre)
                startActivity(intent)
            }
            2 ->{
                val ins=WordManager(applicationContext)
                word = list[pos]
                ins.openWrite()
                var x = ins.deleteWord(word.mot)
                ins.closeBD()
            }
        }
        return super.onContextItemSelected(item)
    }
}