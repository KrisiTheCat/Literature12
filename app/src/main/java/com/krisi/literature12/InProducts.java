package com.krisi.literature12;

import static com.krisi.literature12.products.ProductTheme.LOVE;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.flexbox.FlexboxLayout;
import com.krisi.literature12.products.Product;
import com.krisi.literature12.products.ProductTheme;
import com.krisi.literature12.products.ProductsManager;

import java.util.ArrayList;

public class InProducts extends AppCompatActivity {

    private static final String TAG = "PRODUCT";
    Product product = null;

    ArrayList<Pair<String, String>> texts = new ArrayList<>();
    int currentPage;

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

        String temp = getIntent().getStringExtra("code");
        if(temp != null) {
            product = ProductsManager.encodeProduct(temp);
        }
        else{
            product = ProductsManager.getProduct(LOVE, 1);
        }
        initLayout();

    }

    void initLayout(){
        ((TextView) findViewById(R.id.tvTitle)).setText(product.getTitle());
        ((TextView) findViewById(R.id.tvAuthor)).setText(product.getAuthorName());
        ((TextView) findViewById(R.id.tvText)).setText(product.getText());
        if(product.getGenre().stih){
            ((TextView) findViewById(R.id.tvText)).setGravity(Gravity.CENTER);
        }

        View view;
        view = View.inflate(new ContextThemeWrapper(InProducts.this, product.getTheme().theme), R.layout.widget_tag, null);
        ((LinearLayout) findViewById(R.id.llTheme)).addView(view);
        view = View.inflate(new ContextThemeWrapper(InProducts.this, product.getGenre().theme), R.layout.widget_tag, null);
        ((LinearLayout) findViewById(R.id.llGenre)).addView(view);
        view = View.inflate(new ContextThemeWrapper(InProducts.this, product.getStyle().theme), R.layout.widget_tag, null);
        ((LinearLayout) findViewById(R.id.llStyle)).addView(view);

        FlexboxLayout flPages = ((FlexboxLayout) findViewById(R.id.flPages));

        if (product.getResume() != null){
            (findViewById(R.id.llShortText)).setVisibility(View.GONE);
            addDot(true, flPages);
            texts.add(new Pair<>("Резюме",product.getResume()));
            if(product.getText() != null){
                addDot(false, flPages);
                texts.add(new Pair<>("Оригинален текст",product.getText()));
            }
            else{
                for(Pair<Integer, String> pair : product.getChapters()){
                    addDot(false, flPages);
                    texts.add(new Pair<>("Глава " + pair.first, pair.second));
                }
            }
            currentPage = 0;
            openPage();
        }
        else{
            (findViewById(R.id.clLongText)).setVisibility(View.GONE);
        }
    }

    void addDot(boolean active, FlexboxLayout linearLayout){
        View view = View.inflate(new ContextThemeWrapper(InProducts.this, product.getTheme().theme), R.layout.widget_dot, null);
        view.setLayoutParams(new FlexboxLayout.LayoutParams(20,20));
        view.setId(10000 + (texts.size()));
        if(active) view.setBackgroundTintList(ContextCompat.getColorStateList(InProducts.this, R.color.darkGray));
        linearLayout.addView(view);
    }
    void changeDot(boolean active){
        View view = findViewById(10000 + currentPage);
        if(active) view.setBackgroundTintList(ContextCompat.getColorStateList(InProducts.this, R.color.darkGray));
        else  view.setBackgroundTintList(ContextCompat.getColorStateList(InProducts.this, R.color.mediumGray));
    }

    public void pagesBack(View view){
        changeDot(false);
        currentPage--;
        if(currentPage<0) currentPage+=texts.size();
        changeDot(true);
        openPage();
    }
    public void pagesNext(View view){
        changeDot(false);
        currentPage++;
        if(currentPage>=texts.size()) currentPage-=texts.size();
        changeDot(true);
        openPage();
    }
    public void backBtn(View view){
        finish();
    }

    private void openPage(){
        ((TextView) findViewById(R.id.tvChapter)).setText(texts.get(currentPage).first);
        ((TextView) findViewById(R.id.tvChapterText)).setText(texts.get(currentPage).second);
    }
}