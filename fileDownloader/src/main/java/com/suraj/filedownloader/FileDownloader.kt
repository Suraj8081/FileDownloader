package com.suraj.filedownloader

import android.content.Context
import java.io.*
import java.net.URL
import java.net.URLConnection


class FileDownloader() {

    private var downloadListener: DownloadListener? = null

    fun initDownloadCallback(listener: DownloadListener) {
        this.downloadListener = listener
    }

    fun downloadInPublicDir(url: String, dirName: String) {
        val dir = FileUtils.createDir(dirName);
        downloadFile(url, dir)
    }

    fun downloadInPrivateDir(context: Context, url: String, dirName: String) {
        val dir = FileUtils.createDir(context, dirName);
        downloadFile(url, dir)
    }

    fun getAllFilePublicDir(dirName: String): Array<File> {
        return FileUtils.getAllFilePublicDir(dirName)
    }

    fun getAllFilePrivateDir(context: Context, dirName: String): Array<File> {
        return FileUtils.getAllFilePrivateDir(context, dirName)
    }


    private fun downloadFile(urlString: String, dirName: File) {
        try {
            var count: Int
            val url = URL(urlString)
            val connection: URLConnection = url.openConnection()
            connection.connect()

            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            val lengthOfFile: Int = connection.contentLength

            // download the file
            val input: InputStream = BufferedInputStream(
                url.openStream(),
                8192
            )


            //val fileDir = File(dirName, getFileNameFromURL(urlString))
            val fileDir = FileUtils.createFile(dirName, getFileNameFromURL(urlString), downloadListener)
            if (fileDir.second) {
                val output: OutputStream = FileOutputStream(fileDir.first)
                val data = ByteArray(1024)
                var total: Long = 0
                while ((input.read(data).also { count = it }) != -1) {
                    total += count.toLong()
                    // publishing the progress....
                    downloadListener?.isLoading(((total * 100) / lengthOfFile))
                    downloadListener?.isSuccess(false)
                    // writing data to file
                    output.write(data, 0, count)

                    if (total == lengthOfFile.toLong()) {
                        downloadListener?.isSuccess(true)
                    }

                }
                // flushing output
                output.flush()

                // closing streams
                output.close()
            }
            input.close()

        } catch (exception: Exception) {
            downloadListener?.isError(exception.message.toString())
        }

    }

    private fun getFileNameFromURL(url: String): String {
        val resource = URL(url)
        val host = resource.host
        if (host.isNotEmpty() && url.endsWith(host)) {
            // handle ...example.com
            return ""
        }
        val startIndex = url.lastIndexOf('/') + 1
        val length = url.length

        // find end index for ?
        var lastQMPos = url.lastIndexOf('?')
        if (lastQMPos == -1) {
            lastQMPos = length
        }

        // find end index for #
        var lastHashPos = url.lastIndexOf('#')
        if (lastHashPos == -1) {
            lastHashPos = length
        }

        // calculate the end index
        val endIndex = Math.min(lastQMPos, lastHashPos)
        return url.substring(startIndex, endIndex)
    }


}