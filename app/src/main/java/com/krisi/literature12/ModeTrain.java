package com.krisi.literature12;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.krisi.literature12.animation.AnimationFactory;
import com.krisi.literature12.products.Product;

public class ModeTrain extends AppCompatActivity {

    private static final String TAG = "TRAIN";

    private ImageView btnView;
    private ViewFlipper viewFlipper;
    private boolean isQuoteSide;

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
        initLayout();
        initFlashcard();
    }

    void initLayout(){
        btnView = findViewById(R.id.btnView);
        isQuoteSide = true;

        viewFlipper = findViewById(R.id.vfFlashcard);
        View.OnClickListener flip = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
                if(isQuoteSide){
                    btnView.setImageDrawable(AppCompatResources.getDrawable(ModeTrain.this,R.drawable.opened_eye));
                    findViewById(R.id.btnWrongHide).setAlpha(0);
                    isQuoteSide = false;
                }else{
                    btnView.setImageDrawable(AppCompatResources.getDrawable(ModeTrain.this, R.drawable.closed_eye));
                    findViewById(R.id.btnWrongHide).setAlpha(0.3f);
                    isQuoteSide = true;
                }
            }
        };
        viewFlipper.setOnClickListener(flip);
        btnView.setOnClickListener(flip);

        findViewById(R.id.btnCorrect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainManager.correctAnswer();
                initFlashcard();
            }
        });
        findViewById(R.id.btnWrong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainManager.wrongAnswer();
                initFlashcard();
            }
        });
    }

    void initFlashcard(){
        Pair<String, Product> card = TrainManager.getCardInfo();
        if(card == null){
            Log.d(TAG, "initFlashcard: FINISHED");
            return;
        }
        viewFlipper.setDisplayedChild(0);

        btnView.setImageDrawable(AppCompatResources.getDrawable(ModeTrain.this, R.drawable.closed_eye));
        findViewById(R.id.btnWrongHide).setAlpha(0.3f);
        isQuoteSide = true;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ConstraintLayout clTags = (ConstraintLayout) findViewById(R.id.clTags);
                while(clTags.getChildCount() != 1) clTags.removeViewAt(1);

                ((TextView) findViewById(R.id.tvQuote)).setText(card.first);
                ((TextView) findViewById(R.id.tvTitle)).setText(card.second.getTitle());
                ((TextView) findViewById(R.id.tvAuthor)).setText(card.second.getAuthorName());

                TextView mMyButton = new TextView(new ContextThemeWrapper(ModeTrain.this, card.second.getTheme().theme), null, 0);
                mMyButton.setId(View.generateViewId());
                ((ConstraintLayout) findViewById(R.id.clTags)).addView(mMyButton);
                ((Flow) findViewById(R.id.flow)).addView(mMyButton);
            }
        }, 150);
    }
}