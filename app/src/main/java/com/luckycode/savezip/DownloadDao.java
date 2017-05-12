package com.luckycode.savezip;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Marce Cuevas on 10/05/17.
 */

public class DownloadDao extends GenericDao {
    private static final String TAG=DownloadDao.class.getName();

    public DownloadDao(Context context) {
        super(context);
    }

    public void downloadContent(final TTContent content) {
        TTPath path = new TTPath(getContext(),content);
        content.setPath(path);
        TTAdCreativityREST downloadService = getRetrofit().create(TTAdCreativityREST.class);
        Call<ResponseBody> call = downloadService.downloadFileWithDynamicUrlSync(content.getContentURL());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.e("SUCCESS","SII");
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body(),content.getPath().getZipPath());
                    if (writtenToDisk) {
                        Log.e(TAG, "Creativity downloaded Async");
                        //listener.success(process(content));
                        process(content);
                    }
                } else {
                    Log.e(TAG, "Download error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error");
            }
        });
    }

    private TTContent process(TTContent content) {
        File zipFile = new File(content.getPath().getZipPath());
        File localFile = new File(content.getPath().getLocalPath());
        Log.e("ZIP",zipFile.getAbsolutePath());
        Log.e("LOCAL",localFile.getAbsolutePath());
        if (zipFile.exists()) {
            try {
                ZipUtil.unzip(zipFile, localFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            zipFile.delete();
        }
        return content;
    }


    private boolean writeResponseBodyToDisk(ResponseBody body,String fileName) {
        try {
            File futureStudioIconFile= new File(fileName);
            Log.e("FILE",futureStudioIconFile.getAbsolutePath());

            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            Log.d(TAG, "FAILED TO SAVE " + e.getLocalizedMessage());
            System.out.println("FAILED TO SAVE " + e.getLocalizedMessage());
            e.printStackTrace();
            return false;
        }
    }

    private interface TTAdCreativityREST {
        @GET
        Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    }
}
