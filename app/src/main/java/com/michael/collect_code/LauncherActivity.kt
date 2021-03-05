package com.michael.collect_code

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        findViewById<View>(R.id.bgLauncher).postDelayed(
            {
                startActivity(Intent(this,MainActivity::class.java))
            },1500
        )

    }
}