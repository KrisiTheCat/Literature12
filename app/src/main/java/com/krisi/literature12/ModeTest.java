package com.krisi.literature12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.krisi.literature12.manager.AnswerVisual;
import com.krisi.literature12.manager.TestManager;
import com.krisi.literature12.manager.TestQuestion;

public class ModeTest extends AppCompatActivity {

    private static final String TAG = "TEST";
    private static final int ID_START = 100000;
    int[] answers;
    int[] llAnswersId = {R.id.llAnswer1, R.id.llAnswer2, R.id.llAnswer3, R.id.llAnswer4};
    SpecificMode mode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mode_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mode = (SpecificMode) getIntent().getSerializableExtra("mode");
        initQuestions();
    }

    public void initQuestions(){
        LinearLayout layout = findViewById(R.id.llQuestions);
        answers = new int[ModeSettings.modeManager.QUESTIONS_COUNT];
        for(int i = 0; i < ModeSettings.modeManager.QUESTIONS_COUNT; i++){
            TestQuestion question = ModeSettings.modeManager.getQuestionInfo(i);
            View view = View.inflate(ModeTest.this, R.layout.widget_test_question, null);
            view.setId(ID_START+i);
            layout.addView(view);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
            lp.bottomMargin = 60;
            view.setLayoutParams(lp);
            ((TextView) view.findViewById(R.id.tvTitle)).setText((i + 1) + ". " + question.getQuestion());
            ((TextView) view.findViewById(R.id.tvAnswer1)).setText("А) " + question.getAnswer(0));
            ((TextView) view.findViewById(R.id.tvAnswer2)).setText("Б) " + question.getAnswer(1));
            ((TextView) view.findViewById(R.id.tvAnswer3)).setText("В) " + question.getAnswer(2));
            ((TextView) view.findViewById(R.id.tvAnswer4)).setText("Г) " + question.getAnswer(3));
            answers[i] = -1;

            if(mode==SpecificMode.TEST_REVIEW){
                changeAnswerVisual(view, question.getCorrectAns(), AnswerVisual.CORRECT);
                if(((TestManager) ModeSettings.modeManager).userAnswers[i] != question.getCorrectAns())
                    changeAnswerVisual(view, ((TestManager) ModeSettings.modeManager).userAnswers[i], AnswerVisual.WRONG);
            }
        }
        if(mode == SpecificMode.TEST_REVIEW){
            ((TextView) findViewById(R.id.btnSubmit)).setText(R.string.results_look_results);
        }
    }

    public void clickAnswer(View v) {
        if(mode == SpecificMode.TEST_REVIEW){
            Toast.makeText(ModeTest.this, R.string.toast_cantChangeAns, Toast.LENGTH_SHORT).show();
        }
        else {
            int id = ((View) v.getParent()).getId() - ID_START;
            int ans = -1;
            for (int i = 0; i < 4; i++) {
                if (v.getId() == llAnswersId[i]) ans = i;
            }
            if (answers[id] != -1) {
                changeAnswerVisual((View) v.getParent(), answers[id], AnswerVisual.EMPTY);
            }
            changeAnswerVisual((View) v.getParent(), ans, AnswerVisual.CHECKED);
            answers[id] = ans;
        }
    }

    public void changeAnswerVisual(View parent, int id, AnswerVisual visual){
        View v = parent.findViewById(llAnswersId[id]);
        v.setBackgroundTintList(ContextCompat.getColorStateList(ModeTest.this, visual.colorid));
        ((ImageView) v.findViewWithTag("image")).setImageDrawable(AppCompatResources.getDrawable(ModeTest.this,visual.drawableid));
    }

    public void submitTest(View v){
        if(mode == SpecificMode.TEST_DO) {
            for (int i = 0; i < ModeSettings.modeManager.QUESTIONS_COUNT; i++) {
                if (answers[i] == -1) {
                    ((ScrollView) findViewById(R.id.scrollView)).smoothScrollTo(
                            ((ScrollView) findViewById(R.id.scrollView)).getScrollX(),
                            findViewById(ID_START + i).getTop());
                    Toast.makeText(ModeTest.this, R.string.toast_answerAll, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            ((TestManager) ModeSettings.modeManager).calculateResult(answers);
        }
        Intent in = new Intent(ModeTest.this, ModeResults.class);
        if(mode == SpecificMode.TEST_REVIEW){
            in.putExtra("mode", SpecificMode.TEST_REVIEW);
        }
        else
            in.putExtra("mode", SpecificMode.TEST);
        startActivity(in);
        finish();
    }
}