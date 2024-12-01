package com.krisi.literature12.manager;

import android.util.Pair;

import com.krisi.literature12.products.Product;
import com.krisi.literature12.products.ProductTheme;
import com.krisi.literature12.products.ProductsManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ModeManager {
    public int QUESTIONS_COUNT;
    public int correctCount;
    public int wrongAnswers;
    public ArrayList<Product> allProducts = new ArrayList<>();
    public Map<Integer, Boolean> usedProducts = new HashMap<>(); // productid in allProducts and true - if answered correctly, false - if not
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
//            correctProducts.add(ProductsManager.getProduct(theme, id));
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
    public void wrongAnswerProduct(int id){
        usedProducts.put(id, false);
    }
    public void wrongAnswerProduct(Product prod){
        usedProducts.put(allProducts.indexOf(prod), false);
    }


    public Pair<String, Product> getCardInfo(){return null;} //TRAIN
    public TestQuestion getQuestionInfo(int id){return null;} //TEST
}
