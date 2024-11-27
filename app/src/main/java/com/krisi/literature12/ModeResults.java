package com.krisi.literature12;

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

import org.w3c.dom.Text;

public class ModeResults extends AppCompatActivity {

    ModeManager modeManager = null;
    String type;

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

        type = getIntent().getStringExtra("type");
        if(type.equals("TRAIN")){
            modeManager = ModeTrainSettings.modeManager;
        initLayout();
        }
    }

    void initLayout(){
        if(type.equals("TRAIN")) ((TextView) findViewById(R.id.tvMode)).setText(R.string.mode_train);
        else ((TextView) findViewById(R.id.tvMode)).setText(R.string.mode_test);
        initOverview();
        initProdsToLearn();
        initProdsKnown();
    }

    void initOverview(){
        ((TextView) findViewById(R.id.tvQuestionsCount)).setText(modeManager.QUESTIONS_COUNT+"");
        if(type.equals("TRAIN")){
            ((TextView) findViewById(R.id.tvCorrectLabel)).setText(R.string.knew);
            ((TextView) findViewById(R.id.tvWrongLabel)).setText(R.string.learnt);
        }
        ((TextView) findViewById(R.id.tvQuestionsCorrect)).setText((modeManager.QUESTIONS_COUNT - modeManager.wrongAnswers)+"");
        ((TextView) findViewById(R.id.tvQuestionsWrong)).setText((modeManager.wrongAnswers)+"");
    }

    void initProdsToLearn(){
        LinearLayout layout = findViewById(R.id.llProdsAgain);
        if(modeManager.correctProducts.size() == modeManager.allProducts.size()){
            layout.setVisibility(View.GONE);
            return;
        }
        for(int i = 0; i < modeManager.allProducts.size(); i++){
            if(!modeManager.correctProducts.contains(modeManager.allProducts.get(i))){
                View view = View.inflate(new ContextThemeWrapper(ModeResults.this,
                        modeManager.allProducts.get(i).getTheme().theme),
                        R.layout.widget_product, null);
                ((TextView) view.findViewWithTag("text")).setText("\"" + modeManager.allProducts.get(i).getTitle() + "\" - " + modeManager.allProducts.get(i).getAuthorName());
                layout.addView(view);
            }
        }
    }

    void initProdsKnown(){
        LinearLayout layout = findViewById(R.id.llProdsKnown);
        if(modeManager.correctProducts.isEmpty()){
            layout.setVisibility(View.GONE);
            return;
        }
        for(int i = 0; i < modeManager.correctProducts.size(); i++){
            View view = View.inflate(new ContextThemeWrapper(ModeResults.this,
                            modeManager.correctProducts.get(i).getTheme().theme),
                    R.layout.widget_product, null);
            ((TextView) view.findViewWithTag("text")).setText("\"" + modeManager.correctProducts.get(i).getTitle() + "\" - " + modeManager.correctProducts.get(i).getAuthorName());
            layout.addView(view);
        }
    }
}