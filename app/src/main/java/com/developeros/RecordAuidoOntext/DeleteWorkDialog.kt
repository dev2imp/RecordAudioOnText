package com.developeros.RecordAuidoOntext
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.lang.ClassCastException
class DeleteWorkDialog: DialogFragment() {
    lateinit var addWorkDialogListener: AddWorkDialogListener
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.DeleteTask)
                    .setPositiveButton(R.string.yes,
                            DialogInterface.OnClickListener { dialog, id ->
                                //this interfacve trigers the listener in main activity
                                addWorkDialogListener.onDialogPositiveClick(0)
                            })
                    .setNegativeButton(R.string.no,
                            DialogInterface.OnClickListener { dialog, id ->
                                addWorkDialogListener.onDialogNegativeClick("")
                            })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    override fun onStop() {
        super.onStop()
        addWorkDialogListener.onDialogNegativeClick("")
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            addWorkDialogListener = context as AddWorkDialogListener
        }catch (e: ClassCastException){

        }
    }
}