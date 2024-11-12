package com.krisi.literature12.products;

import android.content.res.Resources;

import com.krisi.literature12.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum  ProductTheme{
    LOVE (R.style.Theme_Literature12_Theme_LOVE),
    HOPE (R.style.Theme_Literature12_Theme_HOPE),
    LABOR(R.style.Theme_Literature12_Theme_LABOR),
    CHOICE(R.style.Theme_Literature12_Theme_CHOICE);

    public int theme;
    ProductTheme(int theme1){
        theme = theme1;
    }

    private static final List<ProductTheme> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    public static ProductTheme randomTheme()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
