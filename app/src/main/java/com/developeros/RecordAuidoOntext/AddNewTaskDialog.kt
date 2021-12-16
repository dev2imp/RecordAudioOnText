package com.developeros.RecordAuidoOntext

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.lang.ClassCastException
class AddNewTaskDialog : DialogFragment() {
    lateinit var addWorkDialogListener: AddWorkDialogListener
    lateinit var editText: EditText
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            editText= EditText(activity)
            val inflater = requireActivity().layoutInflater
            var view : View = inflater.inflate(R.layout.dialog_add_newtask,null)
            editText = view.findViewById(R.id.newTaskName)
            builder.setView(view)
                .setPositiveButton(R.string.add,
                    DialogInterface.OnClickListener { dialog, id ->
                        //this interfacve trigers the listener in main activity
                        Log.d("Expandedwork--AddNewTask-ItemsArryList","")
                        var txt = editText.text.toString()
                        addWorkDialogListener.onDialogPositiveClick(txt)
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->

                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            addWorkDialogListener = context as AddWorkDialogListener
        }catch (e:ClassCastException){

        }
    }
}