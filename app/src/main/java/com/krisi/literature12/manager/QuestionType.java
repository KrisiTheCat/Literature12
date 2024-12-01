package com.krisi.literature12.manager;

import com.krisi.literature12.products.ProductTheme;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum QuestionType {
    AUTHOR_BY_TITLE,
    TITLE_BY_AUTHOR,
    TITLE_BY_GENRE,
    GENRE_BY_TITLE;
    private static final List<QuestionType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    public static QuestionType getRandom()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
