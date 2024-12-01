package com.krisi.literature12.products;

import com.krisi.literature12.R;
import com.krisi.literature12.manager.QuestionType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum ProductGenre {
    RAZKAZ(R.style.Theme_Literature12_Genre_Razkaz, false, R.string.genre_RAZKAZ),
    POEMA(R.style.Theme_Literature12_Genre_Poema, false, R.string.genre_POEMA),
    ELEGIQ(R.style.Theme_Literature12_Genre_Elegiq, true, R.string.genre_ELEGIQ),
    POWEST(R.style.Theme_Literature12_Genre_Powest, false, R.string.genre_POWEST),
    ODA(R.style.Theme_Literature12_Genre_Oda, true, R.string.genre_ODA),
    BALADA(R.style.Theme_Literature12_Genre_Balada, true, R.string.genre_BALADA),
    ROMAN(R.style.Theme_Literature12_Genre_Roman, false, R.string.genre_ROMAN),
    STIHOTWORENIE(R.style.Theme_Literature12_Genre_Stihotvorenie, true, R.string.genre_STIHOTWORENIE);

    public int theme;
    public boolean stih;
    public int nameId;
    ProductGenre(int theme1, boolean stih1, int nameId1){
        theme = theme1;
        stih = stih1;
        nameId = nameId1;
    }
    private static final List<ProductGenre> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    public static ProductGenre getRandom()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
