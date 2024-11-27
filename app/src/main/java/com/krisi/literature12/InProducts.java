package com.krisi.literature12;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.krisi.literature12.products.Product;
import com.krisi.literature12.products.ProductTheme;
import com.krisi.literature12.products.ProductsManager;

public class InProducts extends AppCompatActivity {

    private static final String TAG = "PRODUCT";
    Product product = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_in_products);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();
        String temp = extras.getString("theme");
        ProductTheme theme = ProductTheme.valueOf(temp);
        Integer id = extras.getInt("id");
        product = ProductsManager.getProduct(theme, id);

        initLayout();

    }

    void initLayout(){
        ((TextView) findViewById(R.id.tvTitle)).setText(product.getTitle());
        ((TextView) findViewById(R.id.tvAuthor)).setText(product.getAuthorName());
        ((TextView) findViewById(R.id.tvText)).setText(product.getText());
        if(product.getGenre().stih){
//            ((TextView) findViewById(R.id.tvText)).setGravity(View.TEXT_ALIGNMENT_CENTER);
            ((TextView) findViewById(R.id.tvText)).setGravity(Gravity.CENTER);
        }
    }
}