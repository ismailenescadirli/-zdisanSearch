package com.ismailenescadirli.ozdisan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;

public class ProductsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        Intent dialogIntent = getIntent();
        String link = dialogIntent.getStringExtra("key");
        Thread thread = new Thread(() -> {
            try {
                Document doc = Jsoup.connect(link).get();
                String productMain = doc.select("td.productheadname").text();
                String stock = doc.select("td.stokshowcss").text();
                String image = doc.select("a.productImage").attr("href");
                String description = doc.selectFirst("div.panel-body").select("table").select("tr").get(1).select("td").get(1).text();
                String minPackeking = doc.selectFirst("div.panel-body").select("table").select("tr").get(5).select("td").get(1).text();
                String minOrder = doc.selectFirst("div.panel-body").select("table").select("tr").get(6).select("td").get(1).text();
                Elements element = doc.selectFirst("div.panel-body").select("div").get(1).select("div").get(1).select("div").get(2).select("div");
                final String datasheetLink = element.size()<2 ? null : element.get(1).select("div").get(1).select("a").attr("href");
                runOnUiThread(() -> {
                    updateView(productMain, description, stock, minPackeking, minOrder, datasheetLink, image);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void updateView(String productMain, String description, String stock, String minPackeking, String minOrder, String datasheetLink, String image){

        ImageView imagee = findViewById(R.id.imageView2);
        TextView productname = findViewById(R.id.productNameId);
        TextView productdescription = findViewById(R.id.productDescriptionId);
        TextView stockk= findViewById(R.id.StockId);
        TextView minpacket = findViewById(R.id.minPacketId);
        TextView minorder = findViewById(R.id.minOrderId);
        ImageView datasheetView = findViewById(R.id.pdfId);

        productname.setText(productMain);
        productdescription.setText(description);
        stockk.setText(stock);
        minpacket.setText(minPackeking);
        minorder.setText(minOrder);
        if( datasheetLink == null ){
            datasheetView.setVisibility(View.INVISIBLE);
        } else {
            datasheetView.setOnClickListener (view -> {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(datasheetLink));
                startActivity(i);
            });
        }

        Thread thread1 = new Thread(() -> {
            try {
                InputStream input = new URL(image).openStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                runOnUiThread(() -> imagee.setImageBitmap(myBitmap));
            } catch(Exception ex){
                Log.e("APP", "EX", ex);
            }
        });
        thread1.start();
    }

}