package com.suraj.filedownloader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val TAG=MainActivity::class.java.javaClass.toString()
        val url="https://3fxtqy18kygf3on3bu39kh93-wpengine.netdna-ssl.com/wp-content/uploads/2021/06/1.png";

        findViewById<Button>(R.id.btn).setOnClickListener {
            if (PermissionUtils.getFileMangePermission(this,101)) {
                val fileDownloader= FileDownloader();

                fileDownloader.getAllFilePublicDir("demo")

                GlobalScope.launch {
                   // fileDownloader.downloadInPublicDir(url,"demo")
                    //fileDownloader.downloadInPrivateDir(this@MainActivity,url,"song")
                }

                fileDownloader.initDownloadCallback(object :
                    com.suraj.filedownloader.DownloadListener {
                    override fun isSuccess(success: Boolean) {
                        Log.d(TAG, "isSuccess: "+success)
                    }

                    override fun isLoading(progress: Long) {
                        Log.d(TAG, "isSuccess: "+progress)
                    }

                    override fun isError(error: String) {
                        Log.d(TAG, "isSuccess: "+error)
                    }

                })

            }else{
                Toast.makeText(this,"Permission Denie",Toast.LENGTH_SHORT).show()
            }
        }


    }
}