package com.developeros.RecordAuidoOntext

class StringManipulation {

    fun StringToStringArray(get: String):ArrayList<String>{
        var arr:ArrayList<String> = ArrayList()

        arr =  get.replace("[","")
                .replace("]","")
                .replace("{","")
                .replace("}","")
                .split(",") as ArrayList<String>
        return arr
    }
}