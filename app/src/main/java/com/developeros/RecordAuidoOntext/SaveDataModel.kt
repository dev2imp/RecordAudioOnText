package com.developeros.RecordAuidoOntext

class SaveDataModel {
    var AudioFile=""
    var ParentTitle=""
    var Text=""
    var Recording=""
    var Playing=""
    fun DataToSave(audiofile: String,parenttitle: String,text: String,recording: String,playing: String){
        AudioFile=audiofile
        ParentTitle=parenttitle
        Text=text
        Recording=recording
        Playing=playing
    }
    override fun toString(): String {
        return "{audiofile:'$AudioFile'; parenttitle:'$ParentTitle'; text:'$Text'; recording:'$Recording'; playing:'$Playing'}"
    }

}