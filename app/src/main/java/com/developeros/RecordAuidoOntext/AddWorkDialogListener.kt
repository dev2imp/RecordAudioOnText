package com.developeros.RecordAuidoOntext

interface AddWorkDialogListener {
        fun onDialogPositiveClick(string: String)
        fun onDialogPositiveClick(int: Int)
        fun onDialogNegativeClick(string: String)
        fun ExpandMore(position:Int)
        fun RemooveFragment()
        fun FeedMe(parentTitle: String)
    }
