package com.michael.collect_code

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View

object  DialogUtilsTestmd {

    private var loadingDialog: Dialog? = null

    fun showDialog(dialogHolderApi: DialogHolderApi, cancelBack: () -> Unit = {}, dismissCallBack: () -> Unit = {}){
        if (loadingDialog == null){
            loadingDialog = AlertDialog.Builder(dialogHolderApi.context, R.style.dialog2).setView(dialogHolderApi.rootView()).create()
            loadingDialog?.window!!.decorView.setBackgroundColor(Color.TRANSPARENT)
            loadingDialog?.window!!.decorView.background = null

            dialogHolderApi.handleEvent()
            loadingDialog?.setOnCancelListener {
                loadingDialog = null
                Log.e("szm--","--CancelList---")
                cancelBack.invoke()
            }
            loadingDialog?.setOnDismissListener {
                loadingDialog = null
                Log.e("szm--","--setOnDismissListener---")
                dismissCallBack.invoke()
            }
        }

        loadingDialog!!.apply {
            show()
        }
    }

    fun dismiss(){
        loadingDialog?.apply {
            if (isShowing){
                dismiss()
            }
        }
        loadingDialog = null
    }



    open interface DialogHolderApi{
        val context: Context
        fun rootView():View
        fun inits(view: View)
        fun handleEvent()
    }

    open class DefaultDialogHolder(override val context: Context, val layoutId:Int):
        DialogHolderApi {

        private var  view: View =
            LayoutInflater.from(context).inflate(layoutId, null)

        init {
            inits(view)
        }

        override fun rootView():View{
            return view
        }

        override fun inits(view: View){

        }

        override fun handleEvent(){

        }

    }

}