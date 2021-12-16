package com.developeros.RecordAuidoOntext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
class RecyclewViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private var Items:List<KeyDataModel> = ArrayList();
    var context: Context
    constructor(context:Context,Items: List<KeyDataModel>) : super() {
        this.Items = Items
        this.context= context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_recview,parent,false)
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RecViewHolder ->{
                holder.bind(context,Items.get(position),position)

            }
        }
    }
    override fun getItemCount(): Int {
        return Items.size
    }
    class RecViewHolder constructor(itemView: View):RecyclerView.ViewHolder(itemView){

        fun bind(context:Context,keyDataModel: KeyDataModel,position: Int){
            val work :TextView = itemView.findViewById(R.id.Work)
            val ItemRelLayout :RelativeLayout = itemView.findViewById(R.id.itemRelLayout)
            val deleteTask : ImageView =itemView.findViewById(R.id.deleteItem)
            lateinit var addWorkDialogListener: AddWorkDialogListener
            work.setText(keyDataModel.ParentTitle)
            ItemRelLayout.setOnClickListener(){

                addWorkDialogListener=context as AddWorkDialogListener
                addWorkDialogListener.ExpandMore(position)
            }
            deleteTask.setOnClickListener(){
                    addWorkDialogListener=context as AddWorkDialogListener
                    addWorkDialogListener.onDialogPositiveClick(position)
                }
        }

    }
}