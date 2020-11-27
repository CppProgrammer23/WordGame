package classes

class Word (var mot:String, var taille:Int, var type:String, var genre:String) {
    override fun toString(): String {
        return "Word= '$mot', size='$taille', type='$type',genre='$genre'"
    }
}