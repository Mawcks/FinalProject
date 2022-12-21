package com.matthew.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.content.AsyncTaskLoader;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //fields

    private ArrayList<String> elements = new ArrayList<>();
    private ListAdapter listAdapter;

    //methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load content to screen
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView urlList = findViewById(R.id.urlList);

        MyHTTPRequest req = new MyHTTPRequest();
        req.execute("https://api.nasa.gov/planetary/apod?api_key=HpgsJboIbuFmnl08wQhtaqMbp2zghwpWSmaSadcP");

    }

    //objects

    private static class MyHTTPRequest extends AsyncTask< String, Integer, String>
    {

        @Override
        public String doInBackground(String... args) {

            try {
                URL url = new URL(args[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream response = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(response, StandardCharsets.UTF_8), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                JSONObject nasaPic = new JSONObject(result);

                String nasaPicUrl = nasaPic.getString("URL");

                


            } catch (Exception e) {
                e.printStackTrace();
            }

            publishProgress(25);
            publishProgress(50);
            publishProgress(75);

            return "Done";
        }
        public void onProgressUpdate(Integer ... args){

        }

        public void onPostExecute(String fromDoInBackground) {
            Log.i("HTTP", fromDoInBackground);
        }
    }

    private class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Object getItem(int i) {
            return "This is row " + i;
        }

        @Override
        public long getItemId(int i) {
            return (long) i;
        }

        @Override
        public View getView(int i, View old, ViewGroup viewGroup) {

            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            //new row
            if(newView == null) {
                newView = inflater.inflate(R.layout.row_layout, viewGroup, false);

            }

            TextView tView = newView.findViewById(R.id.rowText);
            tView.setText( getItem(i).toString());

            return newView;
        }
    }
}
