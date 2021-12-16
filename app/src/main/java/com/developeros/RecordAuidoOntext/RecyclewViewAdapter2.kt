package com.developeros.RecordAuidoOntext
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
class RecyclewViewAdapter2 : RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private var Items:ArrayList<SaveDataModel> = ArrayList();
    var context: Context
    constructor(context:Context,Items: ArrayList<SaveDataModel>) : super() {
        this.Items = Items
        this.context= context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_inner_recview,parent,false)
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RecViewHolder ->{
                holder.bind(context,Items,position)
            }
        }
    }
    override fun getItemCount(): Int {
        return Items.size
    }
    class RecViewHolder constructor(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(context:Context,values: ArrayList<SaveDataModel>,position: Int){
            val subtitle :TextView = itemView.findViewById(R.id.subtitle)
            val RecordStop :ImageView = itemView.findViewById(R.id.RecordStop)
            val PlayStop :ImageView = itemView.findViewById(R.id.PlayStop)
            val deleteItem :ImageView = itemView.findViewById(R.id.deleteItem)
            lateinit var itemClickListenerInterface: ItemClickListenerInterface
            subtitle.text=values.get(position).toString()
            if(values.get(position).AudioFile.trim().length>0){
                deleteItem.visibility=View.VISIBLE
                PlayStop.visibility=View.VISIBLE
                deleteItem.isEnabled=true
            }else{
                deleteItem.visibility=View.INVISIBLE
                PlayStop.visibility=View.INVISIBLE
            }
            if(values.get(position).Recording.toBoolean()){
                RecordStop.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_mic_24))
                RecordStop.isEnabled=true
            }else{
                RecordStop.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_mic_off_24))
                RecordStop.isEnabled=true
            }
            if(values.get(position).Playing.toBoolean()){
                PlayStop.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_stop_circle_24))
                PlayStop.isEnabled=true
            }else{
                PlayStop.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_play_circle_24))
                PlayStop.isEnabled=true
            }
            deleteItem.setOnClickListener(){
                itemClickListenerInterface=context as ItemClickListenerInterface
                deleteItem.isEnabled=false
                itemClickListenerInterface.onDeleteClick(values.get(position).ParentTitle,position)
            }
            PlayStop.setOnClickListener(){
                itemClickListenerInterface=context as ItemClickListenerInterface
                PlayStop.isEnabled=false
                if(values.get(position).Playing.toBoolean()){

                    itemClickListenerInterface.onPlayStopClick(values.get(position).ParentTitle,position,
                        false)
                }else{

                    itemClickListenerInterface.onPlayStopClick(values.get(position).ParentTitle,position,
                        true)
                }
            }
            RecordStop.setOnClickListener(){
                itemClickListenerInterface=context as ItemClickListenerInterface
                RecordStop.isEnabled=false
                if(values.get(position).Recording.toBoolean()){

                    itemClickListenerInterface.onRecordStopClick(values.get(position).ParentTitle,position,
                        false)
                }else{

                    itemClickListenerInterface.onRecordStopClick(values.get(position).ParentTitle,position,
                        true)
                }

            }
        }
    }
}