package com.krisi.literature12.manager;

import com.krisi.literature12.R;

public enum AnswerVisual {
    EMPTY(R.color.lightGray, R.drawable.check_empty),
    CHECKED(R.color.testAccent, R.drawable.check_filled),
    CORRECT(R.color.pastel_green, R.drawable.check_correct),
    WRONG(R.color.pastel_red, R.drawable.check_wrong);

    public int colorid, drawableid;

    AnswerVisual(int colorid, int drawableid) {
        this.colorid = colorid;
        this.drawableid = drawableid;
    }
}
