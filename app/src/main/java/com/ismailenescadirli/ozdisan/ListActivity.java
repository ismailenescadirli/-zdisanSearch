package com.ismailenescadirli.ozdisan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ArrayList<Products> arrayList = new ArrayList<>();
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        productsAdapter = new ProductsAdapter(this, R.layout.layout_row, arrayList);
        listView.setAdapter(productsAdapter);

        String mysearch = getIntent().getStringExtra("mysearch");
        String url1 = "https://ozdisan.com/Category/GetSearchCategories?SearchText="+mysearch;
        Thread thread2 = new Thread(() -> {
            try {
                Document doc = Jsoup.connect(url1).get();
                Elements allLinks = doc.select("#layoutYeni a");
                for (Element linkElement : allLinks) {
                    String link = linkElement.attr("href");
                    if(link.startsWith("/p/")) {
                        link = "https://ozdisan.com" + link + "&sayfaAdedi=250";
                        get(link);
                    }
               }
            } catch (Exception ex) {
                Log.e("APP", "EX", ex);
            }
        });
        thread2.start();
    }

    private void get(String url){
        Thread thread = new Thread(() -> {
            try {
                Document doc = Jsoup.connect(url).timeout(60*1000).get();
                Elements select1 = doc.select("#prdTable tr");
                for (Element select2 : select1) {
                    Elements select3 = select2.select("td");
                    if (select3.size() < 5) continue;
                    Element select4 = select3.get(2).select("a").get(0);
                    Element select5 = select3.get(3);
                    Elements select6 = select3.get(4).select("span");
                    String link = select4.absUrl("href");
                    String product = select4.text();

                    String stok = select6.size() == 0 ? "0" : select6.get(0).text();
                    String description = select5.text();
                    arrayList.add(new Products(link, product, stok, description));
                }
                runOnUiThread(() -> {
                    productsAdapter.notifyDataSetChanged();
                });
            } catch (Exception ex) {
                Log.e("APP", "EX", ex);
            }
        });
        thread.start();
    }

}
