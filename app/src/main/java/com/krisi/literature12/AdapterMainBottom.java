package com.krisi.literature12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.krisi.literature12.manager.HistoryManager;

import java.util.ArrayList;

public class AdapterMainBottom extends PagerAdapter {

    private Context mContext;

    public AdapterMainBottom(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = null;
        switch (position){
            case 0:
            case 1:
                layout = (ViewGroup) inflater.inflate(R.layout.widget_main_history, collection, false);
                int avr = -1;
                if(position == 0)
                    avr = HistoryManager.getAverageTrain();
                if(position == 1)
                    avr = HistoryManager.getAverageTest();
                if(avr == -1){
                    layout = (ViewGroup) inflater.inflate(R.layout.widget_main_not_enough, collection, false);
                    break;
                }
                ((TextView) layout.findViewById(R.id.tvAverage)).setText(avr + "%");
                ArrayList<Integer> arr = null;
                if(position == 0) arr= HistoryManager.getHistoryTrain();
                if(position == 1) arr= HistoryManager.getHistoryTest();
                LinearLayout graph = layout.findViewById(R.id.llResults);
                for (int i = 0; i < arr.size(); i++) {
                    View line = inflater.inflate(R.layout.widget_graph_line, graph, false);
                    graph.addView(line);
                    line = graph.getChildAt(graph.getChildCount() - 1);
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) line.findViewWithTag("top").getLayoutParams();
                    lp.weight = arr.get(i);
                    line.findViewById(R.id.viewTop).setLayoutParams(lp);
                    View viewBottom = null;
                    if(position == 0) viewBottom = line.findViewWithTag("bottomTrain");
                    if(position == 1) viewBottom = line.findViewWithTag("bottomTest");
                    viewBottom.setVisibility(View.VISIBLE);
                    lp = (LinearLayout.LayoutParams) viewBottom.getLayoutParams();
                    lp.weight = 100 - arr.get(i);
                    viewBottom.setLayoutParams(lp);
                }
                HorizontalScrollView scrollView = (HorizontalScrollView) layout.findViewById(R.id.scrollView);
                scrollView.scrollTo(graph.getWidth(), 0);
                ViewTreeObserver vto = scrollView.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        scrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        scrollView.fullScroll(View.FOCUS_RIGHT);

                    }
                });
                if(position == 1){
                    layout.findViewById(R.id.viewTransparent).setBackgroundColor(mContext.getColor(R.color.testAccent));
                }
                break;
            default:
                layout = (ViewGroup) inflater.inflate(R.layout.widget_main_not_enough, collection, false);
        }
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return position+" haha";
    }

}