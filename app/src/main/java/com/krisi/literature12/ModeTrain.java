package com.krisi.literature12;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.krisi.literature12.animation.AnimationFactory;
import com.krisi.literature12.products.Product;

public class ModeTrain extends AppCompatActivity {

    private static final String TAG = "TRAIN";

    private ImageView btnView;
    private ViewFlipper viewFlipper;
    private TextView tvCardsDone;
    private ProgressBar pbCards;
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
        tvCardsDone = findViewById(R.id.tvCardsDone);
        pbCards = findViewById(R.id.pbCards);
        isQuoteSide = true;

        viewFlipper = findViewById(R.id.vfFlashcard);
        View.OnClickListener flip = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
                if(isQuoteSide){
                    btnView.setImageDrawable(AppCompatResources.getDrawable(ModeTrain.this,R.drawable.opened_eye));
                    isQuoteSide = false;
                }else{
                    btnView.setImageDrawable(AppCompatResources.getDrawable(ModeTrain.this, R.drawable.closed_eye));
                    isQuoteSide = true;
                }
            }
        };
        viewFlipper.setOnClickListener(flip);
        btnView.setOnClickListener(flip);

        findViewById(R.id.btnCorrect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModeSettings.modeManager.correctAnswer();
                initFlashcard();
                initCardsProgress();
            }
        });
        findViewById(R.id.btnWrong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModeSettings.modeManager.wrongAnswer();
                initFlashcard();
                initCardsProgress();
            }
        });
        initCardsProgress();
    }

    void initFlashcard(){
        Pair<String, Product> card = ModeSettings.modeManager.getCardInfo();
        if(card == null){
            Intent in = new Intent(ModeTrain.this, ModeResults.class);
            in.putExtra("mode", SpecificMode.TRAIN);
            startActivity(in);
            finish();
            return;
        }
        viewFlipper.setDisplayedChild(0);

        btnView.setImageDrawable(AppCompatResources.getDrawable(ModeTrain.this, R.drawable.closed_eye));
        isQuoteSide = true;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ConstraintLayout clTags = (ConstraintLayout) findViewById(R.id.clTags);
                while(clTags.getChildCount() != 1) clTags.removeViewAt(1);

                ((TextView) findViewById(R.id.tvQuote)).setText(card.first);
                ((TextView) findViewById(R.id.tvTitle)).setText(card.second.getTitle());
                ((TextView) findViewById(R.id.tvAuthor)).setText(card.second.getAuthorName());

                View view = null;
                ConstraintLayout layout = findViewById(R.id.clTags);

                ConstraintSet set = new ConstraintSet();

                for(int i = 0; i < 2; i++) {
                    if(i==0){
                        view = View.inflate(new ContextThemeWrapper(ModeTrain.this, card.second.getTheme().theme), R.layout.widget_tag, null);
                        view.setId(R.id.tvStyle);
                    }
                    if(i==1){
                        view = View.inflate(new ContextThemeWrapper(ModeTrain.this, card.second.getGenre().theme), R.layout.widget_tag, null);
                        view.setId(R.id.tvGenre);
                    }
                    layout.addView(view);
                }
            }
        }, 150);
    }

    void initCardsProgress(){
        tvCardsDone.setText(ModeSettings.modeManager.getCorrectCount() + " " +
                getString(R.string.from) + " " +
                ModeSettings.modeManager.getCardsCount() + " " +
                getString(R.string.done));
        pbCards.setProgress((int) ((ModeSettings.modeManager.getCorrectCount()*100/ ModeSettings.modeManager.getCardsCount())), true);
    }
}