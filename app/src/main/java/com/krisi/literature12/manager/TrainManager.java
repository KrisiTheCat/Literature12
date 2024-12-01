package com.krisi.literature12.manager;

import android.util.Pair;

import com.krisi.literature12.products.Product;

import java.util.ArrayList;
import java.util.Collections;

import kotlin.Triple;

public class TrainManager extends ModeManager{

    private  final int QUOTE_MIN_SYMBOLS = 60;
    private  final int QUOTE_MAX_SYMBOLS = 140;
    private  final String TAG = "TRAIN_M";
    public  ArrayList<Triple<String, Product, Boolean>> trainCards = new ArrayList<>();

    public TrainManager(){

    }

    public  void initTrainingSession(ArrayList<String> products, int questions){
        super.initTrainingSession(products,questions);
        trainCards.clear();
        int prodId = random.nextInt(allProducts.size());
        for(int i = 0; i < QUESTIONS_COUNT; i++){
            usedProducts.put(prodId, true);
            trainCards.add(new Triple<>(getRandomQuote(allProducts.get(prodId).getText()), allProducts.get(prodId), true));
            prodId++; prodId%=allProducts.size();
        }
        Collections.shuffle(trainCards);
    }

    public Pair<String, Product> getCardInfo(){
        if(trainCards.isEmpty()) return null;
        return new Pair<>(trainCards.get(0).getFirst(),trainCards.get(0).getSecond());
    }

    public void correctAnswer(){
        trainCards.remove(0);
        correctCount++;
    }
    public  void wrongAnswer(){
        super.wrongAnswerProduct(trainCards.get(0).getSecond());
        if(trainCards.get(0).getThird()){
            super.wrongAnswer();
        }
        if(trainCards.size() == 1) return;
        int newId = 2;
        if(trainCards.size() != 2){
            newId = random.nextInt(trainCards.size()/2)+trainCards.size()/2+1;
        }
        trainCards.add(newId, new Triple<>(trainCards.get(0).getFirst(), trainCards.get(0).getSecond(), false));
        trainCards.remove(0);
    }

    public  String getRandomQuote(String text){
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

    private  int findNextSentenceEnd(String text, int fromIndex) {
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
