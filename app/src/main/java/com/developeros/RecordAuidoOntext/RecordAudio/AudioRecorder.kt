package com.developeros.RecordAuidoOntext.RecordAudio

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import java.io.IOException
class AudioRecorder {
    var recorder: MediaRecorder? = null
    var mediaPlayer :MediaPlayer? = null
    fun AudioRecord() {
        recorder = null
        mediaPlayer=null
    }
    fun startRecord(fileName: String?) {
        Log.e("LOG_TAG", "e.startRecord!!"+ fileName)
        recorder = MediaRecorder()
        recorder!!.setAudioSource(MediaRecorder.AudioSource.DEFAULT)
        recorder!!.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
        recorder!!.setOutputFile(fileName)
        recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
        try {
            recorder!!.prepare()
            recorder!!.start()
        } catch (e: IOException) {

        }
    }
    fun PlayRecord(fileName: String?){
        mediaPlayer= MediaPlayer()
        mediaPlayer?.setDataSource(fileName)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
    }
    fun stopPlay(){
        mediaPlayer?.release()
        mediaPlayer=null
    }
    fun stopRecording() {
        recorder!!.stop()
        recorder!!.release()
        recorder = null
    }
}