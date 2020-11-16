package classes

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context

class WordBdHelper(context:Context?): SQLiteOpenHelper(context, "WordsGame.db",null,1) {
    companion object {
        //val TABLE1 = "Tmot"
        val THE_TABLE = "Tmot"
        val COL_MOT = "mot"
        val COL_TAILLE = "taille"
        val COL_TYPE = "type"
        val COL_GENRE="genre"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_WORDS_TABLE =
            ("CREATE TABLE " + THE_TABLE + "(" + COL_MOT + " TEXT PRIMARY KEY, " + COL_TAILLE + " INTEGER NOT NULL, "
                    + COL_TYPE + " TEXT NOT NULL, " + COL_GENRE+" TEXT NOT NULL"+");")
        db?.execSQL(CREATE_WORDS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}