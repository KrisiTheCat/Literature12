package com.krisi.literature12;

import android.util.Pair;

import com.krisi.literature12.products.Product;
import com.krisi.literature12.products.ProductTheme;
import com.krisi.literature12.products.ProductsManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class ModeManager {
    public int QUESTIONS_COUNT;
    public int correctCount;
    public int wrongAnswers;
    public ArrayList<Product> allProducts = new ArrayList<>();
    public ArrayList<Product> correctProducts = new ArrayList<>();
    public Random random = new Random();


    public void initTrainingSession(ArrayList<String> products, int questions){
        QUESTIONS_COUNT = questions;
        random = new Random();
        wrongAnswers = 0;
        correctCount = 0;
        for(int i = 0; i < products.size(); i++){
            String[] tags = ((String) products.get(i)).split(" ");
            ProductTheme theme = ProductTheme.valueOf(tags[0]);
            int id = Integer.parseInt(tags[1]);
            correctProducts.add(ProductsManager.getProduct(theme, id));
            allProducts.add(ProductsManager.getProduct(theme, id));
        }
    }

    public int getCardsCount(){
        return QUESTIONS_COUNT;
    }

    public int getCorrectCount(){
        return correctCount;
    }

    public void correctAnswer(){}
    public void wrongAnswer(){
        wrongAnswers++;
    }
    public void wrongAnswerProduct(Product prod){
        if(correctProducts.contains(prod)){
            correctProducts.remove(prod);
        }
    }
}
