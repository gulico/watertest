package com.example.wxy.watertest10.View;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Connection;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.wxy.watertest10.Bean.BaseActivity;
import com.example.wxy.watertest10.Bean.News;
import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.presenter.NewsAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends BaseActivity implements View.OnClickListener{

    private List<News> newsList = new ArrayList<>();
    private Handler handler;
    private NewsAdapter adapter;
    private RecyclerView recyclerView;
    private ImageView back;
    private String TAG = "NewsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        back = (ImageView)findViewById(R.id.back) ;
        back.setOnClickListener(this);
        getNews();
        recyclerView = (RecyclerView) findViewById(R.id.recyclervView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    adapter = new NewsAdapter(newsList);
                    recyclerView.setAdapter(adapter);
                }
            }
        };
    }

    private void getNews() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取中国水网头条新闻
                    for (int i = 1; i <= 1; i++) {
                        Log.d(TAG, "run: ");
                        Document doc = Jsoup.connect("http://www.h2o-china.com/news/list?cid=head&page=" + Integer.toString(i)).get();
                        Elements titleLinks = doc.select("div.share");    //解析来获取每条新闻的标题与链接地址
                        Elements descLinks = doc.getElementsByClass("news_ul2 f14 l22 news_list_ul").select("li");//解析来获取每条新闻的简介
                        Elements timeLinks = doc.select("span.fr");   //解析来获取每条新闻的时间与来源
                        Log.e("title", Integer.toString(descLinks.size()));
                        for (int j = 0; j < titleLinks.size(); j++) {
                            //String title = titleLinks.get(j).select("a").text();
                            String title = titleLinks.get(j).attr("title");
                            String uri = titleLinks.get(j).attr("url");
                            String desc = descLinks.get(j).select("a").text();
                            String time = timeLinks.get(j).text();
                            News news = new News(title, uri, desc, time);
                            newsList.add(news);
                        }
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            default:
        }
    }
}
