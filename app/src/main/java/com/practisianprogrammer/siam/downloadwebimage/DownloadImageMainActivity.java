package com.practisianprogrammer.siam.downloadwebimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static android.app.PendingIntent.getActivity;

public class DownloadImageMainActivity extends AppCompatActivity {

    ImageView downloadedImage;
    boolean isNetConnected;
    boolean isInternetConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image_main);


        downloadedImage=(ImageView)findViewById(R.id.imageView);

    }

    private boolean IsNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return isNetConnected=true;
    }

    public boolean IsInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return isInternetConnected=true;

        } catch (Exception e) {
            return false;
        }
    }


    public class ImageDownloader extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url=new URL(urls[0]);

                HttpURLConnection connection=(HttpURLConnection)url.openConnection();

                connection.connect();


                InputStream inputStream=connection.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                return bitmap;

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

    }



    public void DownloadImage(View view) {
        // https://upload.wikimedia.org/wikipedia/en/8/87/Batman_DC_Comics.png


        IsInternetAvailable();
        IsNetworkConnected();

        if(!isInternetConnected && !isNetConnected){
            Toast.makeText(getApplicationContext(),"no internet",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"connected",Toast.LENGTH_SHORT).show();
        }

        ImageDownloader imageDownloader= new ImageDownloader();
        try {
            Bitmap myImage=imageDownloader.execute("https://upload.wikimedia.org/wikipedia/en/8/87/Batman_DC_Comics.png").get();

            downloadedImage.setImageBitmap(myImage);

        } catch (Exception e) {

            e.printStackTrace();

        }

        Log.i("Interaction","Button Tapped");


    }
}
