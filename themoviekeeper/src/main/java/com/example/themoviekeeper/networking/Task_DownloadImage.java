package com.example.themoviekeeper.networking;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.themoviekeeper.R;
import com.example.themoviekeeper.ui.RecyclerViewAdapter_Search;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Xcalibur on 30/12/2016.
 */

public class Task_DownloadImage extends AsyncTask<String, Integer,Bitmap>
{

    private Activity mActivity;
    private ProgressDialog mDialog;
    private RecyclerViewAdapter_Search.MyViewHolder mHolder;

    public Task_DownloadImage(Activity activity, RecyclerViewAdapter_Search.MyViewHolder holder) {

        mHolder = holder;
        mActivity = activity;
        mDialog = new ProgressDialog(mActivity);
    }

    protected Bitmap doInBackground(String... urls) {
        Log.d("doInBackground", "starting download of image");
        Bitmap image = downloadImage(urls[0]);
        return image;
    }

    protected void onPreExecute() {
        //ImageView imageView = (ImageView) mActivity.findViewById(R.id.imageView);
        //imageView.setImageBitmap(null);
        // Reset the progress bar
//        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        mDialog.setCancelable(true);
//        mDialog.setMessage("Loading...");
//        mDialog.setProgress(0);
        //TextView errorMsg = (TextView) mActivity.findViewById(R.id.textView);
        //errorMsg.setVisibility(View.INVISIBLE);
    }

    protected void onProgressUpdate(Integer... progress) {
//        mDialog.show();
//        mDialog.setProgress(progress[0]);
    }

    protected void onPostExecute(Bitmap result) {

        if (result != null) {
            mHolder.iv_poster.setImageBitmap(result);
//            ImageView imageView = (ImageView)mActivity.findViewById(R.id.imageView);
//            imageView.setImageBitmap(result);
        }
        else {
//            TextView errorMsg = (TextView)mActivity.findViewById(R.id.textView);
//            errorMsg.setVisibility(View.VISIBLE);
//            errorMsg.setText("Problem downloading image. Try again later");
        }
        // Close the progress dialog
        //mDialog.dismiss();
    }

    private Bitmap downloadImage(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            InputStream is = httpCon.getInputStream();
            int fileLength = httpCon.getContentLength();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead, totalBytesRead = 0;
            byte[] data = new byte[2048];
            mDialog.setMax(fileLength);
            // Read the image bytes in chunks of 2048 bytes
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
                totalBytesRead += nRead;
                publishProgress(totalBytesRead);
            }
            buffer.flush();
            byte[] image = buffer.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}