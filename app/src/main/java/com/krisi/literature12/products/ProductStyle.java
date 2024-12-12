package com.krisi.literature12.products;

import com.krisi.literature12.R;

public enum ProductStyle {
    MODERNISM(R.style.Theme_Literature12_Style_MODERNISM),
    INDIVIDUALISM(R.style.Theme_Literature12_Style_INDIVIDUALISM),
    EST_INDIVIDUALISM(R.style.Theme_Literature12_Style_EST_INDIVIDUALISM),
    SYMBOLISM(R.style.Theme_Literature12_Style_SYMBOLISM),
    REALISM(R.style.Theme_Literature12_Style_REALISM),
    DIABOLISM(R.style.Theme_Literature12_Style_DIABOLISM),
    ROMANTISM(R.style.Theme_Literature12_Style_ROMANTISM),
    EXPRESSIONISM(R.style.Theme_Literature12_Style_EXPRESSIONISM);

    public int theme;
    ProductStyle(int theme1){
        theme = theme1;
    }
}
