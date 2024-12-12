package com.krisi.literature12.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.krisi.literature12.SpecificMode;
import com.krisi.literature12.products.Product;
import com.krisi.literature12.products.ProductTheme;
import com.krisi.literature12.products.ProductsManager;

import java.util.ArrayList;

public class HistoryManager {

    static SharedPreferences sharedPref;
    static SharedPreferences.Editor editor;

    static ArrayList<Integer> historyTrain = new ArrayList<>();
    static ArrayList<Integer> historyTest = new ArrayList<>();
    static ArrayList<String> historyCollection = new ArrayList<>();
    HistoryManager(){

    }

    public static void init(Context context){
        sharedPref = context.getSharedPreferences("history", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        initArrays();
    }

    private static void initArrays(){
        String train = sharedPref.getString("train", "[]");
        if(train.length()>2) {
            train = train.substring(1, train.length() - 1);
            String[] sa = train.split(", ");
            for (int i = 0; i < sa.length; i++) {
                historyTrain.add(Integer.parseInt(sa[i].trim()));
            }
        }
        String coll = sharedPref.getString("collection", "[]");
        if(coll.length()>2) {
            coll = coll.substring(1, coll.length() - 1);
            String[] sa = coll.split(", ");
            for (int i = 0; i < sa.length; i++) {
                historyCollection.add(sa[i].trim());
            }
        }
    }

    public static void openProduct(ProductTheme theme, int id){
        String prod = ProductsManager.decodeProduct(theme, id);
        openProduct(prod);
    }

    public static void openProduct(String prod){
        historyCollection.remove(prod);
        historyCollection.add(prod);
        while(historyCollection.size() > 5){
            historyCollection.remove(0);
        }
        editor.putString("collection", historyCollection.toString());
        editor.commit();
    }

    public static void addResult(SpecificMode mode, int res){
        if(mode == SpecificMode.TRAIN){
            historyTrain.add(res);
            editor.putString("train", historyTrain.toString());
            editor.commit();
        }
        //TODO
    }

    public static int getAverageTrain(){
        if(historyTrain.isEmpty()) return -1;
        int ans = 0;
        for(int i = 0; i < historyTrain.size(); i++) ans+=historyTrain.get(i);
        return ans / historyTrain.size();
    }
    public static int getAverageTest(){
        if(historyTest.isEmpty()) return -1;
        int ans = 0;
        for(int i = 0; i < historyTest.size(); i++) ans+=historyTest.get(i);
        return ans / historyTest.size();
    }

    public static ArrayList<String> getHistoryCollection() {
        return historyCollection;
    }

    public static ArrayList<Integer> getHistoryTrain() {
        return historyTrain;
    }

    public static ArrayList<Integer> getHistoryTest() {
        return historyTest;
    }
}
