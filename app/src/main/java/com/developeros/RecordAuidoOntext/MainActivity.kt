package com.developeros.RecordAuidoOntext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developeros.RecordAuidoOntext.RecordAudio.AudioRecorder
import java.io.File
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
class MainActivity :
        AppCompatActivity(),
        View.OnClickListener,
        AddWorkDialogListener,
    ItemClickListenerInterface{
    var audioRecorder = AudioRecorder()
    var fragment = ExapandedWorkFragment()
    lateinit var recyclerView:RecyclerView
    lateinit var recyclewViewAdapter: RecyclewViewAdapter
    var saveDataModel= SaveDataModel()
    lateinit var addItem : ImageView
    lateinit var mainRelativeLayout:RelativeLayout
    var dataToDisk = DataToDisk(this)//save data to arraylist to later save to file
    var deleteIndex: Int = -1
    var parentTitle =""
    var newTask: AddNewTaskDialog = AddNewTaskDialog()
    var deleteWorkDialog = DeleteWorkDialog()
    var deleteAudioDialog =DeleteAudioDialog()
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addItem = findViewById(R.id.Add)
        mainRelativeLayout=findViewById(R.id.mainRelativeLayout)
        addItem.setOnClickListener(this)
    }
    override fun onStart(){
        super.onStart()
        try {
            dataToDisk.UserData=ArrayList()
            dataToDisk.UserHashData = HashMap()
            dataToDisk.ReadfromDisk()
        }catch (e :Exception){
        }
        iniRecView()
    }
    override fun onStop() {
        super.onStop()
        dataToDisk.run()
    }
    private fun AddNewTask(parentTitle: String){
        saveDataModel= SaveDataModel()
        //parentTitle is also used as Key in DatatoDisk.userHasmap
        //I may want to chnage that key to something else which is unique
        saveDataModel.DataToSave("",parentTitle
            ,"This is the text I have to read and record my voice", false.toString(),false.toString())
        /*
        in here I have to check if this parentTitle already exist
        if it exist then I shoudnt add this keyDataModel to
       dataToDisk.UserData becasue that will be added as sub item
         */
        dataToDisk.isThisParentNew(parentTitle)
        // put this new item to hashmap anyway
        dataToDisk.PutTohashMap(parentTitle,saveDataModel)
        recyclerView.adapter=null
        iniRecView()
    }
    private fun iniRecView(){
        recyclerView=findViewById(R.id.RecView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclewViewAdapter= RecyclewViewAdapter(this,dataToDisk.UserData)
        recyclerView.adapter = recyclewViewAdapter
    }
    override fun onClick(v: View?) {
        if(v?.id  == R.id.Add){
            newTask.show(supportFragmentManager,"Dialog")
        }
    }
    override fun onDialogPositiveClick(string: String) {
        AddNewTask(string)
    }

    override fun onDialogPositiveClick(int: Int) {
        //this function is called when user clicks delete and when clicks ok to delete
        //that is why I set deletedIndex to -1 initially then set it to index that was clicked
        //if user agree with delete the item will be removed
        if(deleteIndex==-1){
            //display dialog
            deleteIndex=int;
            deleteWorkDialog.show(supportFragmentManager,"Dialog")
        }else{
            //remove item
            dataToDisk.UserHashData.remove(dataToDisk.UserData.get(deleteIndex).ParentTitle)
            dataToDisk.UserData.removeAt(deleteIndex)
            deleteIndex=-1
            iniRecView()
        }
    }

    override fun onDialogNegativeClick(string: String) {
        deleteIndex=-1
    }
    override fun ExpandMore(position: Int) {
        var parentTitle= dataToDisk.UserData.get(position).ParentTitle
        if(dataToDisk.UserHashData.get(parentTitle)!=null){
            //if we will go new fragment we need to first save data to disk later
                //we may need to read id again from disk
                    //we may need to make this async task wait for it to wirte to disk
                        //then continue
            mainRelativeLayout.visibility=View.INVISIBLE
            var bundle = Bundle()
            bundle.putString(getString(R.string.DataFomActivityToFragment),
                parentTitle)
            fragment.arguments=bundle
            supportFragmentManager!!.beginTransaction().
            add(R.id.FragmentContainer,fragment,"Fragment").commit()
        }
    }
    override fun RemooveFragment() {
        var fragment: Fragment = supportFragmentManager?.
           findFragmentById(R.id.FragmentContainer)!!
           supportFragmentManager?.
            beginTransaction()?.remove(fragment)?.commit()
        mainRelativeLayout.visibility=View.VISIBLE
    }
    override fun FeedMe(parentTitle: String) {
        fragment.ItemsArryList=dataToDisk.UserHashData.get(parentTitle)!!
    }
    override fun onPlayStopClick(parentTitle: String,index: Int, play: Boolean) {
       //play audio if exist
        var AudioFile = dataToDisk.UserHashData.get(parentTitle)?.get(index)?.AudioFile.toString()
        var file = File(AudioFile)
        if(file.exists()){
            if(play){
                var i = 0
                for(items in dataToDisk.UserHashData.get(parentTitle)!!){
                    if(items.Playing.toBoolean()){
                        if(audioRecorder.mediaPlayer!=null){
                            audioRecorder.stopPlay()
                        }
                        dataToDisk.UserHashData.get(parentTitle)?.get(i)?.Playing=false.toString()
                        items.Playing=false.toString()
                        fragment.UpdateItemAt(i,items)
                    }
                    i++
                }
                audioRecorder.PlayRecord(AudioFile)
                audioRecorder.mediaPlayer?.setOnCompletionListener {
                    //after play was completed set mediaplayer to null
                    audioRecorder.stopPlay()
                    //then update in UI
                    UpdateUIandDataOnPlayStop(parentTitle,AudioFile,false.toString(),index)
                }
            }else{
                audioRecorder.stopPlay()
            }
        }else{
            AudioFile=""
        }
        UpdateUIandDataOnPlayStop(parentTitle,AudioFile,play.toString(),index)
    }
    fun UpdateUIandDataOnPlayStop(parentTitle:String,audioFile:String,play:String,index:Int){
        saveDataModel= SaveDataModel()
        saveDataModel.DataToSave(
                audioFile,
                dataToDisk.UserHashData.get(parentTitle)?.get(index)?.ParentTitle.toString(),
                dataToDisk.UserHashData.get(parentTitle)?.get(index)?.Text.toString(),
                dataToDisk.UserHashData.get(parentTitle)?.get(index)?.Recording.toString(),
                play.toString(),
        )
        //update data mainly
        dataToDisk.UserHashData.get(parentTitle)?.set(index,saveDataModel)
        //update data for UI
        fragment.UpdateItemAt(index,saveDataModel)
    }
    override fun onRecordStopClick(parentTitle: String,index: Int, record: Boolean) {
        var AudioFile = dataToDisk.UserHashData.get(parentTitle)?.get(index)?.AudioFile.toString()
        //record audio
        if(record){
            //if any other item already records should stop
                var i = 0
            for(items in dataToDisk.UserHashData.get(parentTitle)!!){
                if(items.Recording.toBoolean()){
                    if(audioRecorder.recorder!=null){
                        audioRecorder.stopRecording()
                    }
                    dataToDisk.UserHashData.get(parentTitle)?.get(i)?.Recording=false.toString()
                    items.Recording=false.toString()
                    fragment.UpdateItemAt(i,items)
                }
                i++
            }
            AudioFile = Environment.getExternalStorageDirectory().absolutePath+"/"+index+".3gp"
            audioRecorder.startRecord(AudioFile)
        }else{
            //stop recording
            audioRecorder.stopRecording()
        }
        saveDataModel= SaveDataModel()
        saveDataModel.DataToSave(
            AudioFile,
            dataToDisk.UserHashData.get(parentTitle)?.get(index)?.ParentTitle.toString(),
            dataToDisk.UserHashData.get(parentTitle)?.get(index)?.Text.toString(),
            record.toString(),
            dataToDisk.UserHashData.get(parentTitle)?.get(index)?.Playing.toString(),
        )
        //update data mainly
        dataToDisk.UserHashData.get(parentTitle)?.set(index,saveDataModel)
        //update data for UI
        fragment.UpdateItemAt(index,saveDataModel)
        Log.d("MainActicvity---> dataToDisk.UserHashData-->", dataToDisk.UserHashData.toString())
    }
    override fun onDeleteClick(parentTitle: String,index: Int) {
        //delete recorded audio
        if(deleteIndex==-1){
            deleteIndex=index
            this.parentTitle =parentTitle
            deleteAudioDialog.show(supportFragmentManager,"Dialog")
            //user confirm delection for index from dialog
        }else{
            //remove audio for given index
                var AudioFile=""
            var file = File(dataToDisk.UserHashData.get(this.parentTitle)?.get(deleteIndex)?.AudioFile)
            if(file.exists()){
                file.delete()
            }
            UpdateUIandDataOnPlayStop(this.parentTitle,AudioFile,false.toString(),deleteIndex)
            deleteIndex=-1
        }
    }

    override fun OnNegativeClick() {
       deleteIndex=-1
    }
}