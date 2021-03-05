package com.michael.collect_code

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

// 这里修改机制可以监听开屏|锁屏广播

class IconUtils {

    val alias = mutableSetOf(
        "com.michael.collect_code.MoRenIconActivity",
        "com.michael.collect_code.DyIconActivity"
    )

    @SuppressLint("WrongConstant")
    fun setIcon(ctx:Context, enabledActivity:String){
        val packageManager: PackageManager = ctx.packageManager
        alias.forEach {
            packageManager.setComponentEnabledSetting(
                ComponentName(ctx,it),
                fetchStat(it,enabledActivity),
                PackageManager.DONT_KILL_APP)
        }

        // kill掉桌面app,让它自动重启。以便观察到动态更换后的Icon ----实际中不需要，launcher监听机制，修改后几秒（大约十秒）后会自动修改
//        val am = ctx.getSystemService(Activity.ACTIVITY_SERVICE)  as ActivityManager
//        val intent = Intent(Intent.ACTION_MAIN)
//        intent.addCategory(Intent.CATEGORY_HOME)
//        intent.addCategory(Intent.CATEGORY_DEFAULT)
//        val resolves =
//            packageManager.queryIntentActivities(intent, 0)
//        for (res in resolves) {
//            if (res.activityInfo != null) {
//                am.killBackgroundProcesses(res.activityInfo.packageName)
//            }
//        }

//        val alias1 = "com.michael.collect_code.MoRenIconActivity"
//        packageManager.setComponentEnabledSetting(
//            ComponentName(ctx,alias1),
//            fetchStat(alias1,enabledActivity),
//            PackageManager.DONT_KILL_APP)


    }

    private fun fetchStat(alias : String , targetActivity:String):Int{
        return if (alias == targetActivity) PackageManager.COMPONENT_ENABLED_STATE_ENABLED else PackageManager.COMPONENT_ENABLED_STATE_DISABLED
    }

}