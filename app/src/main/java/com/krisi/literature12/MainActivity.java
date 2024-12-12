package com.krisi.literature12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.krisi.literature12.manager.HistoryManager;
import com.krisi.literature12.products.ProductsManager;

import java.util.ArrayList;
import java.util.List;
import android.animation.ArgbEvaluator;

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
        HistoryManager.init(getApplicationContext());

        ViewPager viewPager;
        AdapterMainTop adapter;
        List<Model> models;
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        models = new ArrayList<>();
        models.add(new Model(
                R.drawable.icon_train,
                R.drawable.round_edges_gradient_train,
                R.string.mode_train,
                R.string.mode_train_descr));
        models.add(new Model(
                R.drawable.icon_test,
                R.drawable.round_edges_gradient_test,
                R.string.mode_test,
                R.string.mode_test_descr));
        models.add(new Model(
                R.drawable.icon_collection,
                R.drawable.round_edges_gradient_collection,
                R.string.mode_collection,
                R.string.mode_collection_descr));

        adapter = new AdapterMainTop(models, this, MainActivity.this);

        ViewPager viewPagerContent  = findViewById(R.id.vpContent);
        viewPagerContent.setAdapter(new AdapterMainBottom(this, MainActivity.this));
        viewPagerContent.setOffscreenPageLimit(4);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter((PagerAdapter) adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors = {
                getResources().getColor(R.color.trainingLight),
                getResources().getColor(R.color.testLight),
                getResources().getColor(R.color.collectionLight)
        };


        View topColor = findViewById(R.id.vTopColor);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                viewPagerContent.scrollTo((int) (viewPagerContent.getWidth() * (position + positionOffset)), 0);
                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
                    topColor.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    topColor.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ViewPager viewPager  = findViewById(R.id.viewPager);
        ViewPager viewPagerContent  = findViewById(R.id.vpContent);
        viewPagerContent.setAdapter(new AdapterMainBottom(this, MainActivity.this));
        viewPagerContent.setOffscreenPageLimit(3);
        viewPagerContent.setCurrentItem(viewPager.getCurrentItem());
    }
}

//TODO save training preferences