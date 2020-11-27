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


    fun deleteWord(m:String):Int{
        return db.delete(WordBdHelper.THE_TABLE,WordBdHelper.COL_MOT+" LIKE ?", arrayOf(m))//?
    }
    fun updateWord(m:String, w:Word):Int{
        val values=ContentValues()
        values.put(WordBdHelper.COL_MOT, w.mot)
        values.put(WordBdHelper.COL_TYPE,w.type)
        values.put(WordBdHelper.COL_GENRE,w.genre)
        values.put(WordBdHelper.COL_TAILLE,w.mot.length)
        return db.update(WordBdHelper.THE_TABLE,values,"mot LIKE ?",arrayOf(m))
    }
    fun createTabScore(){
        val createQuery =  "CREATE TABLE IF NOT EXISTS "+WordBdHelper.THE_SCORE+" (score INTEGER);"
        try{
            //cursor=db.rawQuery(createQuery,null)
            db.execSQL(createQuery)
        }
        catch(e:SQLiteException){
            //db.execSQL(createQuery)
            e.printStackTrace()
        }
    }
    fun insertScore(sc:Int):Long{
        val values = ContentValues()

        values.put("score",sc)
        return wordHelp.writableDatabase.insert(WordBdHelper.THE_SCORE,null,values)
    }
    fun getScore():Int{
        val selectQuery =  "SELECT score FROM "+ WordBdHelper.THE_SCORE+" ORDER BY score DESC LIMIT 1;"
        var cursor :Cursor?=null
        try{
            cursor=db.rawQuery(selectQuery,null)
        }
        catch(e:SQLiteException){
            db.execSQL(selectQuery)
        }
        var getsc: Int
        cursor!!.moveToPosition(0)
        getsc=cursor.getInt(cursor.getColumnIndex("score"))
        return getsc
    }
    fun getRandomNoun():String{
        val selectQuery =  "SELECT mot FROM "+WordBdHelper.THE_TABLE+" WHERE type LIKE ?"
        var cursor :Cursor?=null

        try{
            cursor=db.rawQuery(selectQuery, arrayOf("Noun"))
        }
        catch(e:SQLiteException){
            db.execSQL(selectQuery)
        }
        val sizeCursor = cursor!!.count
        val rnd = (0 until sizeCursor-1).random()
        var setMot=""
        cursor.moveToPosition(rnd)
        setMot=cursor.getString(cursor.getColumnIndex("mot"))
        return setMot
    }
    fun getRandomAdj():String{
        val selectQuery =  "SELECT mot FROM "+WordBdHelper.THE_TABLE+" WHERE type LIKE ?"
        var cursor :Cursor?=null

        try{
            cursor=db.rawQuery(selectQuery, arrayOf("Adjective"))

        }
        catch(e:SQLiteException){
            db.execSQL(selectQuery)

        }
        val sizeCursor = cursor!!.count
        val rnd = (0 until sizeCursor-1).random()
        var setMot=""
        cursor.moveToPosition(rnd)
        setMot=cursor.getString(cursor.getColumnIndex("mot"))
        return setMot
    }
    fun dropTab():Unit{
        val selectQuery =  "DROP TABLE IF EXISTS "+WordBdHelper.THE_SCORE+";"

        try{
            db.execSQL(selectQuery)
        }
        catch(e: SQLiteException){
            e.printStackTrace()
        }
    }
}