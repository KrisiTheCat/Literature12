package com.krisi.literature12;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.krisi.literature12.manager.HistoryManager;
import com.krisi.literature12.products.ProductTheme;
import com.krisi.literature12.products.ProductsManager;

public class ModeCollection extends AppCompatActivity {

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
            View view = View.inflate(new ContextThemeWrapper(ModeCollection.this, theme.theme), R.layout.widget_collection_theme, llThemes);
            view = llThemes.getChildAt(llThemes.getChildCount()-1);
            int[] ids = {R.id.tvProduct1,R.id.tvProduct2,R.id.tvProduct3};
            TextView textView = null;
            for(int i = 0; i < ProductsManager.productCount(theme);i++){
                textView = ((TextView) view.findViewById(ids[i]));
                textView.setText(
                        "• \"" + ProductsManager.getProduct(theme, i).getTitle() + "\" - "+ ProductsManager.getProduct(theme, i).getAuthorName());
                int finalI = i;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HistoryManager.openProduct(theme, finalI);

                        Intent i = new Intent(ModeCollection.this, InProducts.class);
                        i.putExtra("code", ProductsManager.decodeProduct(theme, finalI));
                        startActivity(i);
                    }
                });
            }
        }
    }
}