package com.suraj.filedownloader

import android.content.Context
import android.os.Build
import android.os.Environment
import java.io.File

internal object FileUtils {
    private val ROOT_DIR_PUBLIC = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        "${Environment.getStorageDirectory()}/emulated/0"
    } else {
        Environment.getExternalStorageDirectory()
    }.toString()

    fun createDir(dirName: String): File {
        val file = File(ROOT_DIR_PUBLIC, dirName)
        if (!file.exists()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                file.createNewFile()
            }else
                file.mkdir()
        }
        return file
    }

    fun createDir(context: Context,dirName: String?):File{
        val file=context.getExternalFilesDir(dirName)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            file!!.createNewFile()
        }else
            file!!.mkdir()
        return file
    }

    fun getAllFilePublicDir(dirName: String):Array<File>{
        val file = File(ROOT_DIR_PUBLIC, dirName)
        return file.listFiles() as Array<File>
    }

    fun getAllFilePrivateDir(context: Context,dirName: String):Array<File>{
        val file=context.getExternalFilesDir(dirName)
        return file!!.listFiles() as Array<File>
    }

}