package com.luckycode.savezip;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Marce Cuevas on 10/05/17.
 */

public class TTPath{
    private String localPath;
    private String zipPath;

    public TTPath(Context context, TTContent content) {
        this.localPath = getApplicationName(context);
        this.zipPath = localPath + ".zip";
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public void setZipPath(String zipPath) {
        this.zipPath = zipPath;
    }

    public String getLocalPath() {
        return localPath;
    }

    public String getZipPath() {
        return zipPath;
    }

    public static String getApplicationName(Context context) {
        String appName = context.getPackageName();
        String dirPath = "/unilever/" + appName;
        File dirFile;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            dirFile = new File(android.os.Environment.getExternalStorageDirectory() + dirPath);
            //dirFile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), dirPath);
            Log.e("ENTRE ACA",dirFile.getAbsolutePath());
        } else {
            dirFile = new File(Environment.getDataDirectory() + dirPath);
        }

        if (!dirFile.exists()){
            dirFile.mkdirs();
        }

        return dirFile.getAbsolutePath();
    }
}
