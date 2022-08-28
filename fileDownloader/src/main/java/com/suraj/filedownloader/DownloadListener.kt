package com.suraj.filedownloader

interface DownloadListener {
    fun isSuccess(success:Boolean)
    fun isLoading(progress:Long)
    fun isError(error:String)
}