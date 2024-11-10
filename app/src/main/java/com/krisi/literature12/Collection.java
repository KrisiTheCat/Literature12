package com.krisi.literature12;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.krisi.literature12.products.Product;
import com.krisi.literature12.products.ProductTheme;
import com.krisi.literature12.products.ProductsManager;

import java.util.ArrayList;

public class Collection extends AppCompatActivity {

    private String TAG = "COLLECTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_collection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initLayout();
    }

    void initLayout(){
        LinearLayout llThemes = ((LinearLayout) findViewById(R.id.llThemes));
        for(ProductTheme theme : ProductTheme.values()){
            View view = View.inflate(new ContextThemeWrapper(Collection.this, theme.theme), R.layout.widget_collection_theme, llThemes);
            view = llThemes.getChildAt(llThemes.getChildCount()-1);
            if(ProductsManager.productCount(theme) == 3) {
                ((TextView) view.findViewById(R.id.tvProduct1)).setText(ProductsManager.getProduct(theme, 0).getTitle());
                ((TextView) view.findViewById(R.id.tvProduct2)).setText(ProductsManager.getProduct(theme, 1).getTitle());
                ((TextView) view.findViewById(R.id.tvProduct3)).setText(ProductsManager.getProduct(theme, 2).getTitle());
            }
        }
    }
}