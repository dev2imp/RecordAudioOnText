package com.developeros.RecordAuidoOntext
import android.content.Context
import org.json.JSONObject
class DataToDisk(var context: Context?): Thread() {
    var UserData:ArrayList<KeyDataModel> = ArrayList()
    var UserHashData:HashMap<String,ArrayList<SaveDataModel>> = HashMap()
    lateinit var keyDataModel :KeyDataModel
     override fun run() {
        super.run()
         context?.openFileOutput(context?.getString(R.string.Filename),Context.MODE_PRIVATE)
               .use {
                   it?.write(UserHashData.toString().toByteArray())
               }
         UserHashData = HashMap()
    }
    fun ReadfromDisk(){
        //read from there.
        UserHashData = HashMap()
        UserData= ArrayList()
       var text= context?.openFileInput(context?.getString(R.string.Filename))?.bufferedReader()?.useLines {
            lines -> lines.fold("") { some, text ->  some+text  }
        }
        if (text != null) {
            addSavedWork(text)
        }
    }
    fun addSavedWork(text:String){
        var stringManipulation = StringManipulation()
        var savedDate = stringManipulation.StringToStringArray(text+",")//to convert it to an array
        var jsonObject:JSONObject
        var audiofile = ""
        var parenttitle = ""
        var text = ""
        var recording = ""
        var playing = ""
        for (item in savedDate){
            if(item.trim().length>0){
                var It=""
                try {
                    It=item.split("=")[1].replace(";",",")

                }catch (e:Exception){
                    It=item.replace(";",",")

                }

                jsonObject = JSONObject("{"+It+"}")
                audiofile = jsonObject.get(context?.getString(R.string.audiofile)) as String
                parenttitle = jsonObject.get(context?.getString(R.string.parenttitle)) as String
                text = jsonObject.get(context?.getString(R.string.text)) as String
                recording = jsonObject.get(context?.getString(R.string.recording)) as String
                playing = jsonObject.get(context?.getString(R.string.playing)) as String
                var saveDataModel = SaveDataModel()
                saveDataModel.DataToSave(audiofile,parenttitle,text,recording,playing)
                PutTohashMap(parenttitle,saveDataModel)
                isThisParentNew(parenttitle)
            }
        }
    }
    fun isThisParentNew(parentTitle:String){
        var flag=true
        //we want to display once each main task name
        for(it in UserData){
            if(it.ParentTitle.equals(parentTitle)){
                flag=false
                break
            }
        }
        if(flag){
            keyDataModel = KeyDataModel(parentTitle)
            UserData.add(keyDataModel)
        }
    }

    fun PutTohashMap(ParentTitle:String,saveDataModel: SaveDataModel){
        UserHashData.put(ParentTitle,saveDataModel)
    }
    //extension funciton  UserHashData.put(work,saveDataModel)
    fun <K, V> HashMap<K, V>.put(key: K, value: SaveDataModel) {
        var arr = ArrayList<SaveDataModel>()
        if(UserHashData.containsKey(key)){

             UserHashData.get(key)?.add(value)
        }else{
            arr.add(value)
            UserHashData.put(key.toString(),arr)
        }
    }
}







