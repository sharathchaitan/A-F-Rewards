package com.example.aberchrombie.shar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.aberchrombie.shar.retrofit.ProductAsynchronous;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyler_view_layout);
        RecyclerView recyclerView = findViewById(R.id.recyler_view);
        ProductAsynchronous productAsynchronous = new ProductAsynchronous(getApplicationContext(), recyclerView);
        productAsynchronous.execute(BuildConfig.BASE_URL);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        SharedPreferences sharedPreferences = this.getSharedPreferences("Key", MODE_PRIVATE);
        intent.setData(Uri.parse(sharedPreferences.getString("url", "")));
        startActivity(intent);
    }
}
