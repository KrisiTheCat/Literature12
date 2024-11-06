package com.krisi.literature12.products;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum  ProductTheme{
    HOPE;


    private static final List<ProductTheme> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    public static ProductTheme randomTheme()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
