package com.krisi.literature12;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.krisi.literature12.animation.AnimationFactory;
import com.krisi.literature12.products.Product;

public class ModeTrain extends AppCompatActivity {

    private static final String TAG = "TRAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mode_train);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initFlashcard(TrainManager.trainCards.get(0));

        ViewFlipper viewFlipper = findViewById(R.id.vfFlashcard);
        viewFlipper.setOnClickListener(v -> {
            AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
//            viewFlipper.showNext();
        });


    }

    void initFlashcard(Pair<String, Product> card){
        ((TextView) findViewById(R.id.tvQuote)).setText(card.first);
//        ((TextView) findViewById(R.id.tvQuote)).setLines((card.first.split("\n", -1).length));
//        Log.d(TAG, "initFlashcard: "+(card.first.split("\n", -1).length));
        ((TextView) findViewById(R.id.tvTitle)).setText(card.second.getTitle());
        ((TextView) findViewById(R.id.tvAuthor)).setText(card.second.getAuthorName());
    }
}