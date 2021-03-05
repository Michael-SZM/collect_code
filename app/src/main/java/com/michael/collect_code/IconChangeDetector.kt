package com.michael.collect_code

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter


object IconChangeDetector {

    private val screenBroadcastReceiver = ScreenBroadcastReceiver()

    /**
     * add listener
     */
    fun addDetector(ctx:Context){
        registerListener(ctx)
    }

    fun removeDetector(ctx:Context){
        ctx.unregisterReceiver(screenBroadcastReceiver)
    }

    /**
     * 启动screen状态广播接收器
     */
    private fun registerListener(ctx:Context) {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_USER_PRESENT)
        ctx.registerReceiver(screenBroadcastReceiver, filter)
    }

    /**
     * screen状态广播接收者
     */
    private class ScreenBroadcastReceiver : BroadcastReceiver() {
        private var action: String? = null
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {
            action = intent.action
            when(action){
                Intent.ACTION_SCREEN_ON->{// 开屏
                    IconUtils().setIcon(ctx = context,enabledActivity = conditions())
                }
                Intent.ACTION_SCREEN_OFF->{// 锁屏
                    IconUtils().setIcon(ctx = context,enabledActivity = conditions())
                }
                Intent.ACTION_USER_PRESENT->{// 解锁

                }
            }
        }

        private fun conditions():String{
            val timeMillions = System.currentTimeMillis()
            return "com.michael.collect_code.DyIconActivity"
        }

    }

}