package com.michael.collect_code

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class GlideDownloadedShowActivity : AppCompatActivity() {

    val testUrl = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3350497021,3015906272&fm=26&gp=0.jpg"

    val pictures = mutableListOf(
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3350497021,3015906272&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3160107510,784197751&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1421402195,4170141515&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3655392981,1460418657&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1056337878,44094981&fm=26&gp=0.jpg"
    )

    private val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val REQUEST_PERMISSION_CODE = 23

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide_downloaded_show)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
        }

        val imageView = findViewById<ImageView>(R.id.img_test)
        findViewById<Button>(R.id.btn_download).setOnClickListener {

            pictures.forEachIndexed { index, s ->
                Observable.create(object : ObservableOnSubscribe<File>{
                    override fun subscribe(emitter: ObservableEmitter<File>) {
                        emitter.onNext(Glide.with(this@GlideDownloadedShowActivity).load(s).downloadOnly(SIZE_ORIGINAL, SIZE_ORIGINAL).get())
                        emitter.onComplete()
                    }

                }).subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.newThread())
                        .subscribe(object :Consumer<File>{
                            override fun accept(file: File?) {
                                //获取到下载得到的图片，进行本地保存
                                val pictureFolder: File = Environment.getExternalStorageDirectory()
                                val appDir = File(pictureFolder, "your_picture_save_path")
                                if (!appDir.exists()) {
                                    appDir.mkdirs()
                                }
                                val fileName = "test$index.jpg"
                                val destFile = File(appDir, fileName)
                                //把gilde下载得到图片复制到定义好的目录中去
                                copy(file, destFile)
                                imageView.post {
                                    Toast.makeText(this@GlideDownloadedShowActivity, "download success $index", Toast.LENGTH_SHORT).show()
                                }
                                // 最后通知图库更新
                                // 最后通知图库更新
                                sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                        Uri.fromFile(File(destFile.path))))
                            }

                        })
            }



        }

        findViewById<Button>(R.id.btn_load_from_local).setOnClickListener {
            //本地文件
            val dir = File("${Environment.getExternalStorageDirectory()}${File.separator}your_picture_save_path")
            if (dir.exists() && dir.isDirectory){
                Toast.makeText(this, "文件个数----${dir.listFiles().size}", Toast.LENGTH_SHORT).show()
                if (dir.listFiles().size < 5){
                    return@setOnClickListener
                }
            }

//            File file = new File(Environment.getExternalStorageDirectory(), "xiayu.png");
            val file = File("${Environment.getExternalStorageDirectory()}${File.separator}your_picture_save_path", "test1.jpg")
            //加载图片
            Glide.with(this).load(file).into(imageView)

//            Glide.with(this).asGif().load(file).into(imageView)
        }

    }


    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    fun copy(source: File?, target: File?) {
        if (target != null && target.exists().not()){
            target.createNewFile()
        }
        var fileInputStream: FileInputStream? = null
        var fileOutputStream: FileOutputStream? = null
        try {
            fileInputStream = FileInputStream(source)
            fileOutputStream = FileOutputStream(target)
            val buffer = ByteArray(1024)
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileInputStream?.close()
                fileOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            permissions.forEachIndexed { index, s ->
                Log.i("MainActivity", "申请的权限为：" + permissions[index] + ",申请结果：" + grantResults[index])
            }
        }
    }

}