package classes

class Word (var mot:String, var taille:Int, var type:String, var genre:String) {
    override fun toString(): String {
        return "Word(he word= '$mot', taille='$taille', type='$type',genre='$genre')"
    }
    fun getWord():String{
        return mot
    }
    fun get_type():String{
        return type
    }
    fun getSize():Int{
        return taille
    }
    fun get_genre():String{
        return genre
    }
}
