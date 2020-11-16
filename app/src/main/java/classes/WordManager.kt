package classes

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.net.Uri


class WordManager(context: Context) {
    private var wordHelp=WordBdHelper(context)
    lateinit var db:SQLiteDatabase
    fun openReadBD(){
        db=wordHelp.readableDatabase
    }
    fun openWrite(){
        db=wordHelp.writableDatabase
    }
    fun closeBD(){
        db.close()
    }
    fun addWord(mot:Word):Long{
        val values = ContentValues()

        values.put(WordBdHelper.COL_MOT,mot.mot)
        values.put(WordBdHelper.COL_TAILLE,mot.mot.toString().length)
        values.put(WordBdHelper.COL_GENRE,mot.genre)
        values.put(WordBdHelper.COL_TYPE,mot.type)
        return wordHelp.writableDatabase.insert(WordBdHelper.THE_TABLE,null,values)
    }
    //fun cursorToWords(cursor: Cursor):List<Word>{

    //}

    fun getWords():List<Word>{
        val wordList: ArrayList<Word> = ArrayList()
        val selectQuery =  "SELECT * FROM "+WordBdHelper.THE_TABLE+";"
        var cursor :Cursor?=null
        try{
            cursor=db.rawQuery(selectQuery,null)
        }
        catch(e:SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var setMot: String
        var setType: String
        var setGenre:String
        if(cursor.moveToFirst()){
            do{
                setMot=cursor.getString(cursor.getColumnIndex("mot"))
                setType=cursor.getString(cursor.getColumnIndex("type"))
                setGenre=cursor.getString(cursor.getColumnIndex("genre"))
                val wr=Word(mot=setMot,taille= setMot.length,type=setType,genre=setGenre)
                wordList.add(wr)
            }while(cursor.moveToNext())
        }
        return wordList
    }

    //fun getWords(t:Int):List<Word>{

    //}
    //fun getWords(type:String, g:String):List<Word>{

    //}

    fun deleteWord(m:String):Int{
        return db.delete(WordBdHelper.THE_TABLE,WordBdHelper.COL_MOT+"=", arrayOf(m))//?
    }
    fun updateWord(m:String, w:Word):Int{
        val values=ContentValues()
        values.put(WordBdHelper.COL_MOT, w.mot)
        values.put(WordBdHelper.COL_TYPE,w.type)
        values.put(WordBdHelper.COL_GENRE,w.genre)
        values.put(WordBdHelper.COL_TAILLE,m.length)
        return db.update(WordBdHelper.THE_TABLE,values,"mot="+m,null)
    }
}