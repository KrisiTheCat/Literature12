package com.krisi.literature12.products;

import com.krisi.literature12.R;

public enum ProductGenre {
    RAZKAZ(R.style.Theme_Literature12_Genre_Razkaz),
    POEMA(R.style.Theme_Literature12_Genre_Poema),
    ELEGIQ(R.style.Theme_Literature12_Genre_Elegiq),
    POWEST(R.style.Theme_Literature12_Genre_Powest),
    ODA(R.style.Theme_Literature12_Genre_Oda),
    BALADA(R.style.Theme_Literature12_Genre_Balada),
    ROMAN(R.style.Theme_Literature12_Genre_Roman),
    STIHOTWORENIE(R.style.Theme_Literature12_Genre_Stihotvorenie);

    public int theme;
    ProductGenre(int theme1){
        theme = theme1;
    }
}
