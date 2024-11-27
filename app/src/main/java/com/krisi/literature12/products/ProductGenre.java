package com.krisi.literature12.products;

import com.krisi.literature12.R;

public enum ProductGenre {
    RAZKAZ(R.style.Theme_Literature12_Genre_Razkaz, false),
    POEMA(R.style.Theme_Literature12_Genre_Poema, false),
    ELEGIQ(R.style.Theme_Literature12_Genre_Elegiq, true),
    POWEST(R.style.Theme_Literature12_Genre_Powest, false),
    ODA(R.style.Theme_Literature12_Genre_Oda, true),
    BALADA(R.style.Theme_Literature12_Genre_Balada, true),
    ROMAN(R.style.Theme_Literature12_Genre_Roman, false),
    STIHOTWORENIE(R.style.Theme_Literature12_Genre_Stihotvorenie, true);

    public int theme;
    public boolean stih;
    ProductGenre(int theme1, boolean stih1){
        theme = theme1;
        stih = stih1;
    }
}
