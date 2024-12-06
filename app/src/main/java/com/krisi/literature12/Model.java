package com.krisi.literature12;

public class Model {

    private int image;
    private int drawable;
    private int title;
    private int desc;

    public Model(int image, int drawable, int title, int desc) {
        this.image = image;
        this.drawable = drawable;
        this.title = title;
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }
}