package com.krisi.literature12;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterMainTop extends PagerAdapter {

    private List<Model> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterMainTop(List<Model> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.widget_main_tab, container, false);

        ((TextView) view.findViewById(R.id.tvTitle)).setText(models.get(position).getTitle());
        ((TextView) view.findViewById(R.id.tvDescription)).setText(models.get(position).getDesc());

        ImageView imageView = ((ImageView) view.findViewById(R.id.ivButton));
        imageView.setImageResource(models.get(position).getImage());
        imageView.setBackgroundResource(models.get(position).getDrawable());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra("param", models.get(position).getTitle());
//                context.startActivity(intent);
//                // finish();
            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}