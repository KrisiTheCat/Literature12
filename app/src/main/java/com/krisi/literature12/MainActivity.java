package com.krisi.literature12;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.krisi.literature12.products.ProductsManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ProductsManager.init(getApplicationContext());
    }

    public void openTraining(View view){
        Intent in = new Intent(MainActivity.this, ModeTrainSettings.class);
        startActivity(in);
    }
    public void openCollection(View view){
        Intent in = new Intent(MainActivity.this, Collection.class);
        startActivity(in);
    }
}

//TODO save training preferences