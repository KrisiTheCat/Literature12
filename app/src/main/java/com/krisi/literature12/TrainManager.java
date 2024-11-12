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
    private static final int QUOTE_MIN_SYMBOLS = 60;
    private static final int QUOTE_MAX_SYMBOLS = 140;
    private static int correctCount;
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
        correctCount = 0;
    }

    public static Pair<String, Product> getCardInfo(){
        if(trainCards.isEmpty()) return null;
        return trainCards.get(0);
    }

    public static int getCardsCount(){
        return QUESTIONS_COUNT;
    }

    public static int getCorrectCount(){
        return correctCount;
    }

    public static void correctAnswer(){
        trainCards.remove(0);
        correctCount ++;
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

        int end = findNextSentenceEnd(text, start);
        while (end != -1 && end - start < QUOTE_MIN_SYMBOLS) {
            end = findNextSentenceEnd(text, end+1);
        }

        if (end == -1) {
            end = text.length();
        }

        if (end - start >QUOTE_MAX_SYMBOLS) {
            return getRandomQuote(text);
        }

        return text.substring(start, end);
    }

    private static int findNextSentenceEnd(String text, int fromIndex) {
        int nextNewline = text.indexOf('\n', fromIndex);
        int nextPeriod = text.indexOf('.', fromIndex);
        int nextQuestion = text.indexOf('?', fromIndex);
        int nextExclamation = text.indexOf('!', fromIndex);

        // Find the nearest of the sentence-ending characters
        int end = -1;
        if (nextNewline != -1) end = nextNewline;
        if (nextPeriod != -1 && (end == -1 || nextPeriod < end)) end = nextPeriod+1;
        if (nextQuestion != -1 && (end == -1 || nextQuestion < end)) end = nextQuestion+1;
        if (nextExclamation != -1 && (end == -1 || nextExclamation < end)) end = nextExclamation+1;

        return end;
    }
}
