package com.developeros.RecordAuidoOntext

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class ExapandedWorkFragment : Fragment(), View.OnClickListener{
    lateinit var recyclerView: RecyclerView
    lateinit var recyclewViewAdapter2 :RecyclewViewAdapter2
    lateinit var backArrow:ImageView
    lateinit var addWorkDialogListener: AddWorkDialogListener
    var ParentTitle:String=""
    var state:Boolean=false
    var ItemsArryList:ArrayList<SaveDataModel> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view:View?=null
        view=inflater!!.inflate(R.layout.fragment_workexpanded,container,false)
        ParentTitle= requireArguments().getString(getString(R.string.DataFomActivityToFragment),"")
        recyclerView=view.findViewById(R.id.RecView2)
        backArrow=view.findViewById(R.id.BackArrow)
        backArrow.setOnClickListener(this)
        addWorkDialogListener=context as AddWorkDialogListener
        //fragments says that it is ready to get data from activity
        ItemsArryList=ArrayList()
        Log.d("Fragment on start","-->")
        addWorkDialogListener.FeedMe(ParentTitle)
        iniRecView()
        state=true
        return view
    }

    override fun onStop() {
        super.onStop()
        state=false
    }

    fun UpdateItemAt(int:Int, data:SaveDataModel){
        ItemsArryList.set(int,data)
        recyclewViewAdapter2.notifyItemChanged(int)
    }
    private fun iniRecView(){
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclewViewAdapter2= RecyclewViewAdapter2(requireContext(),ItemsArryList)
        recyclerView.adapter = recyclewViewAdapter2
    }
    override fun onClick(v: View?) {
        if(v!!.id==R.id.BackArrow){
            addWorkDialogListener=context as AddWorkDialogListener
            addWorkDialogListener.RemooveFragment()
        }
    }
}

