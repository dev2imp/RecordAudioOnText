package com.developeros.RecordAuidoOntext

interface ItemClickListenerInterface {
    fun onPlayStopClick(parentTitle: String,index: Int,play:Boolean)
    fun onRecordStopClick(parentTitle:String,index: Int,record:Boolean)
    fun onDeleteClick(parentTitle:String,index: Int)
    fun OnNegativeClick()
}