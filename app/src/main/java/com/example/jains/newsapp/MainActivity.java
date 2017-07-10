package com.example.jains.newsapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Adler32;

public class MainActivity extends AppCompatActivity {

    JSONArray arr;
    //ArrayList<NewsComp> newsArr;
    ArrayList<String> newsTitle;
    ArrayList<String> newsUrl;
    ArrayAdapter arrayAdapter;
    ListView listView;

    /*public class NewsComp
    {
        int id;
        String title;
        String url;

        NewsComp()
        {
            id = 0;
            title = "";
            url = "";
        }
    }
*/
    public class DownloadTask extends AsyncTask<String ,Void ,String>
    {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";

            URL url;
            HttpURLConnection urlConnection = null;

            try
            {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data!=-1)
                {
                    char current = (char)data;
                    result += current;
                    data = reader.read();

                }
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //String a;
            Log.i("Content",result);

            try {
                //JSONObject jsonObject = new JSONObject(result);
                arr = new JSONArray(result);

                int c= arr.length();
                String ch = String.valueOf(c);
                Log.i("Array Length",ch);

                for(int i=0;i<arr.length();i++)
                {
                    DownloadNews getNews = new DownloadNews();

                    getNews.execute("https://hacker-news.firebaseio.com/v0/item/"+arr.getString(i)+".json?print=pretty");
                    //JSONObject jsonData= arr.getJSONObject(i);
                    //a = jsonData.getString(i);
                    //int newsInt = arr.getInt(i);
                    //Integer newsID = new Integer(newsInt);
                    //String r = String.valueOf(a);
                    //Log.i("Array Info"+i,r);
                    //*if(a!=null)*//*
                    //newsArr.add(newsID);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public class DownloadNews extends AsyncTask<String ,Void ,String >
    {
        @Override
        protected String doInBackground(String... urls) {

            String result = "";

            URL url;
            HttpURLConnection urlConnection = null;

            try
            {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data!=-1)
                {
                    char current = (char)data;
                    result += current;
                    data = reader.read();

                }
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //NewsComp newsC = new NewsComp();
            try {
                JSONObject jsonObject = new JSONObject(s);
                /*if(jsonObject!=null) {*/
                    String newsT = jsonObject.getString("title");
                    String newsU = jsonObject.getString("url");

                    /*newsC.id = jsonObject.getInt("id");
                    newsC.title= newsTitle;
                    newsC.url = newsUrl;
*/
                    Log.i("News Title", newsT);
                    Log.i("News Url", newsU);

                    //newsArr.add(newsC);
                    newsTitle.add(newsT);
                    newsUrl.add(newsU);

                arrayAdapter.notifyDataSetChanged();
                //}
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");

        newsTitle = new ArrayList<>();
        newsUrl = new ArrayList<>();

        listView = (ListView)findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,newsTitle);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent= new Intent(MainActivity.this,NewsFeed.class);
                Toast.makeText(MainActivity.this,newsUrl.get(i),Toast.LENGTH_SHORT).show();
                intent.putExtra("url",newsUrl.get(i));
                startActivity(intent);
            }
        });

    }

    /*public void listItemClicked(int i)
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }*/
}
