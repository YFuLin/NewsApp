package com.example.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<News> newsList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "http://v.juhe.cn/weixin/query?key=007bd6689229c51bea01dd5d1d12c3bf";
        RxVolley.get(url, new HttpCallback(){
            @Override
            public void onSuccess(String t) {
                Toast.makeText(getApplicationContext(),"成功",Toast.LENGTH_SHORT).show();
                Log.i("MainActivity", "Json" + t);
                parsingJson(t);
            }
        });
        NewsAdapter adapter=new NewsAdapter(MainActivity.this,R.layout.news_item,newsList);
        ListView listView=(ListView)findViewById(R.id.list_main);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news=newsList.get(position);
                Toast.makeText(MainActivity.this,"跳转中...",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,ContentActivity.class);
                intent.putExtra(ContentActivity.LINK_NAME,news.getUrl());
                startActivity(intent);
            }
        });

    }

    //解析Json
    private void parsingJson(String t) {

        /*try {
            JSONArray jsonArray=new JSONArray(t);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String conTent=jsonObject.getString("KeyWord");
                newContent.setText(conTent);
        }catch (Exception e){
            e.printStackTrace();
        }*/
        try {
            JSONObject jsonObhect = new JSONObject(t);
            JSONObject jsonresult = jsonObhect.getJSONObject("result");
            JSONArray jsonArray = jsonresult.getJSONArray("list");
            //拿到返回值
            for (int i=0; i < jsonArray.length(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                String title = object.getString("title");
                String source = object.getString("source");
                String firstImg = object.getString("firstImg");
                String url = object.getString("url");
                News a=new News(title,source,firstImg,url);
                newsList.add(a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
