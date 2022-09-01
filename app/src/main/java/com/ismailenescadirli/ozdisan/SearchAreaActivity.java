package com.ismailenescadirli.ozdisan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SearchAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_area);

        EditText searchTextEdit = findViewById(R.id.searchAreaId);
        Button btnSearch = findViewById(R.id.button);
        btnSearch.setOnClickListener(view -> {
            String searchString = searchTextEdit.getText().toString().trim();
            if( searchString.isEmpty() ) return;

            Intent search = new Intent(SearchAreaActivity.this, ListActivity.class);
            search.putExtra("mysearch",searchString);
            startActivity(search);
        });
    }
}