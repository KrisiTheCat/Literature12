package com.krisi.literature12;

import android.util.Log;
import android.util.Pair;

import com.krisi.literature12.products.Product;
import com.krisi.literature12.products.ProductTheme;
import com.krisi.literature12.products.ProductsManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class TrainManager {

    private static final int QUESTIONS_COUNT = 10;
    private static final String TAG = "TRAIN_M";
    public static ArrayList<Pair<String, Product>> trainCards = new ArrayList<>();
    private static Random random = new Random();

    TrainManager(){

    }

    public static void initTrainingSession(){
        random = new Random();
        for(int i = 0; i < QUESTIONS_COUNT; i++){
            ProductTheme theme = ProductTheme.randomTheme();
            int id = random.nextInt(ProductsManager.productCount(theme));
            Product product = ProductsManager.getProduct(theme, id);
            trainCards.add(new Pair<>(getRandomQuote(product.getText()), product));
        }
    }

    public static Pair<String, Product> getCardInfo(){
        if(trainCards.isEmpty()) return null;
        return trainCards.get(0);
    }

    public static void correctAnswer(){
        trainCards.remove(0);
    }
    public static void wrongAnswer(){
        int newId = random.nextInt(trainCards.size()/2)+trainCards.size()/2;
        trainCards.add(newId, trainCards.get(0));
        trainCards.remove(0);
    }

    public static String getRandomQuote(String text){
        int position = random.nextInt(text.length());

        int start = -1;
        for (int i = position; i >= 0; i--) {
            if (Character.isUpperCase(text.charAt(i))) {
                start = i;
                break;
            }
        }

        if (start == -1) {
            return getRandomQuote(text);
        }

        int end = text.indexOf('\n', start);
        while (end != -1 && end - start < 60) {
            end = text.indexOf('\n', end + 1);
        }

        if (end == -1) {
            end = text.length();
        }

//        int previousNewline = text.lastIndexOf('\n', position - 1);
//        if(previousNewline == -1) previousNewline = 0;
//        int nextNewline = text.indexOf('\n', position);
//        if(nextNewline == -1) nextNewline = text.length()-1;
//
//        while (nextNewline - previousNewline < 60){
//            if(previousNewline != 0) {
//                previousNewline = text.lastIndexOf('\n', previousNewline - 1);
//                if(previousNewline == -1) previousNewline = 0;
//            }
//            else{
//                nextNewline = text.indexOf('\n', nextNewline+1);
//            }
//        }
        return text.substring(start, end);
    }
}
