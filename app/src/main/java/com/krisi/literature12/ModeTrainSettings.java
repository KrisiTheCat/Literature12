package com.krisi.literature12;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.flexbox.FlexboxLayout;
import com.krisi.literature12.products.Product;
import com.krisi.literature12.products.ProductTheme;
import com.krisi.literature12.products.ProductsManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ModeTrainSettings extends AppCompatActivity {

    private static final String TAG = "TRAIN_SETTINGS";
    SeekBar seekBar;
    TextView tvQuestions;

    private Set<String> productsUsed = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mode_train_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initLayout();
    }

    void initLayout(){
        seekBar = findViewById(R.id.seekBar);
        tvQuestions = findViewById(R.id.tvQuestions);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress %18 == 0) tvQuestions.setVisibility(View.INVISIBLE);
                else tvQuestions.setVisibility(View.VISIBLE);
                tvQuestions.setText((progress*5+10)+"");

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) findViewById(R.id.vOrientation).getLayoutParams();
                params.horizontalBias = ((float) progress) /18; // here is one modification for example. modify anything else you want :)
                findViewById(R.id.vOrientation).setLayoutParams(params);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        FlexboxLayout layout  = findViewById(R.id.fbProducts);
//        Flow flow = findViewById(R.id.flow);
        Product product = null;

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String[] tags = ((String) v.getTag()).split(" ");
//                ProductTheme theme = ProductTheme.valueOf(tags[0]);
//                int id = Integer.parseInt(tags[1]);
                String tag = (String) v.getTag();
                TextView textView = v.findViewWithTag("text");
                if(productsUsed.contains(tag)){
                    productsUsed.remove(tag);
                    v.findViewWithTag("disabled").setVisibility(View.VISIBLE);
                    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else{
                    productsUsed.add(tag);
                    v.findViewWithTag("disabled").setVisibility(View.INVISIBLE);
                    textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        };

        for(ProductTheme theme : ProductTheme.values()){
            if(ProductsManager.products.containsKey(theme)){
                for(int i  = 0; i < ProductsManager.products.get(theme).size(); i++){
                    product = ProductsManager.products.get(theme).get(i);
                    View view = View.inflate(new ContextThemeWrapper(ModeTrainSettings.this, product.getTheme().theme), R.layout.widget_tag, null);
                    view.setId(View.generateViewId());
                    ((TextView) view.findViewWithTag("text")).setText(product.getTitle());
                    view.setOnClickListener(onClickListener);
                    view.setTag(theme+" "+i);
                    productsUsed.add(theme+" "+i);
                    layout.addView(view);
//                    flow.addView(view);
                }
            }
        }

    }
}