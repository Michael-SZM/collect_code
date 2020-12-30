package com.michael.collect_code

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun showDialog() {
        val dialogHolder = object :
            DialogUtilsTestmd.DefaultDialogHolder(this, R.layout.dialog_layout_test) {

            private lateinit var msg: TextView
            private lateinit var cancel: Button

            override fun inits(view: View) {
                super.inits(view)
                msg = view.findViewById(R.id.id_dialog_loading_msg)
                cancel = view.findViewById(R.id.btn_cancel)
            }

            override fun handleEvent() {
                super.handleEvent()
                cancel.setOnClickListener {
                    DialogUtilsTestmd.dismiss()
                }
            }

        }
        DialogUtilsTestmd.showDialog(dialogHolder, cancelBack = {
            Log.e("dia--", "cancel  dialog")
        }, dismissCallBack = {
            Log.e("dia--", "dismiss  dialog")
        })
    }
}