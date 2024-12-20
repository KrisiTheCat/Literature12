package com.krisi.literature12;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.krisi.literature12.manager.HistoryManager;
import com.krisi.literature12.manager.ModeManager;

public class ModeResults extends AppCompatActivity {

    ModeManager modeManager = null;
    private SpecificMode mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mode_results);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mode = (SpecificMode) getIntent().getSerializableExtra("mode");
        modeManager = ModeSettings.modeManager;
        initLayout();
    }

    void initLayout(){
        if(mode == SpecificMode.TRAIN)((TextView) findViewById(R.id.tvMode)).setText(R.string.mode_train);
        else ((TextView) findViewById(R.id.tvMode)).setText(R.string.mode_test);
        initOverview();
        initProdsToLearn();
        initProdsKnown();
    }

    void initOverview(){
        ((TextView) findViewById(R.id.tvQuestionsCount)).setText(modeManager.QUESTIONS_COUNT+"");
        if(mode == SpecificMode.TRAIN){
            ((TextView) findViewById(R.id.tvCorrectLabel)).setText(R.string.knew);
            ((TextView) findViewById(R.id.tvWrongLabel)).setText(R.string.learnt);
            (findViewById(R.id.clLookTest)).setVisibility(View.GONE);
        }
        else{
            ((ProgressBar) findViewById(R.id.progressBar)).setIndeterminateTintList(ColorStateList.valueOf(getResources().getColor(R.color.trainingLight)));
            (findViewById(R.id.clLookTest)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(ModeResults.this, ModeTest.class);
                    in.putExtra("mode", SpecificMode.TEST_REVIEW);
                    startActivity(in);
                    finish();
                }
            });
        }
        int correct = modeManager.QUESTIONS_COUNT - modeManager.wrongAnswers;
        int percent = correct*100 / modeManager.QUESTIONS_COUNT;
        ((TextView) findViewById(R.id.tvQuestionsCorrect)).setText(correct+"");
        ((TextView) findViewById(R.id.tvQuestionsWrong)).setText((modeManager.wrongAnswers)+"");
        ((TextView) findViewById(R.id.tvPercent)).setText(percent + "%");
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, percent); // see this max value coming back here, we animate towards that value
        animation.setDuration(1000); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        HistoryManager.addResult(mode, percent);
//        ((ProgressBar) findViewById(R.id.progressBar)).setProgress(percent);
    }

    void initProdsToLearn(){
        LinearLayout layout = findViewById(R.id.llProdsAgain);
        for(int i = 0; i < modeManager.allProducts.size(); i++){
            if(Boolean.FALSE.equals(modeManager.usedProducts.get(i))){
                View view = View.inflate(new ContextThemeWrapper(ModeResults.this,
                        modeManager.allProducts.get(i).getTheme().theme),
                        R.layout.widget_product, null);
                ((TextView) view.findViewWithTag("text")).setText("\"" + modeManager.allProducts.get(i).getTitle() + "\" - " + modeManager.allProducts.get(i).getAuthorName());
                layout.addView(view);
            }
        }
        if(layout.getChildCount() == 1)
            layout.setVisibility(View.GONE);
    }

    void initProdsKnown(){
        LinearLayout layout = findViewById(R.id.llProdsKnown);
        for(int i = 0; i < modeManager.allProducts.size(); i++){
            if(Boolean.TRUE.equals(modeManager.usedProducts.get(i))) {
                View view = View.inflate(new ContextThemeWrapper(ModeResults.this,
                                modeManager.allProducts.get(i).getTheme().theme),
                        R.layout.widget_product, null);
                ((TextView) view.findViewWithTag("text")).setText("\"" + modeManager.allProducts.get(i).getTitle() +
                        "\" - " + modeManager.allProducts.get(i).getAuthorName());
                layout.addView(view);
            }
        }

        if(layout.getChildCount() == 1)
            layout.setVisibility(View.GONE);
    }

}