
Add this on your build.gradle file

Latest Relese is [![](https://jitpack.io/v/Suraj8081/FileDownloader.svg)](https://jitpack.io/#Suraj8081/FileDownloader

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  	add this dependency
	
	
  	dependencies {
  		...........
	        implementation 'com.github.Suraj8081:FileDownloader:$version'
	}
  
  
  Code is
  
  	findViewById<Button>(R.id.btn).setOnClickListener {
	
            if (PermissionUtils.getFileMangePermission(this,101)) {
	    
                val fileDownloader= FileDownloader();

                fileDownloader.getAllFilePublicDir("demo")

                GlobalScope.launch {
                    //when you save file in our internal storage then
                    fileDownloader.downloadInPublicDir(url,"Demo")
                    
                    //when you save file in our package storage like android directory
                    fileDownloader.downloadInPrivateDir(this@MainActivity,url,"Domo")
                }

                fileDownloader.initDownloadCallback(object : DownloadListener {
                    override fun isSuccess(success: Boolean) {
                        //get Downalod Status
                        Log.d(TAG, "isSuccess: $success")
                    }

                    override fun isLoading(progress: Long) {
                        //getProgres
                        Log.d(TAG, "isLoading: $progress")
                    }

                    override fun isError(error: String) {
                    //get Downalod Error
                        Log.d(TAG, "isSuccess: $error")
                    }

                })

            }else{
                Toast.makeText(this,"Permission Deni",Toast.LENGTH_SHORT).show()
            }


