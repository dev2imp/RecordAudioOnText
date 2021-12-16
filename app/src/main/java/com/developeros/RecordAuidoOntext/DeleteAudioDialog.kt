package com.developeros.RecordAuidoOntext
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.lang.ClassCastException
class DeleteAudioDialog: DialogFragment() {
    lateinit var itemClickListenerInterface: ItemClickListenerInterface
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.DeleteAudio)
                    .setPositiveButton(R.string.yes,
                            DialogInterface.OnClickListener { dialog, id ->
                                //this interfacve trigers the listener in main activity
                                itemClickListenerInterface.onDeleteClick("",-1)
                            })
                    .setNegativeButton(R.string.no,
                            DialogInterface.OnClickListener { dialog, id ->
                                itemClickListenerInterface.OnNegativeClick()
                            })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    override fun onStop() {
        super.onStop()
        itemClickListenerInterface.OnNegativeClick()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            itemClickListenerInterface = context as ItemClickListenerInterface
        }catch (e: ClassCastException){

        }
    }
}