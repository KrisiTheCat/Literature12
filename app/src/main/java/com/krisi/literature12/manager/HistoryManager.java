package com.krisi.literature12.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.krisi.literature12.SpecificMode;

import java.util.ArrayList;

public class HistoryManager {

    static SharedPreferences sharedPref;
    static SharedPreferences.Editor editor;

    static ArrayList<Integer> historyTrain = new ArrayList<>();
    static ArrayList<Integer> historyTest = new ArrayList<>();
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
                System.out.println("history train: " + Integer.parseInt(sa[i]));
            }
            System.out.println("history train length: " +historyTrain.size());
        }
    }

    public static void addResult(SpecificMode mode, int res){
        if(mode == SpecificMode.TRAIN){
            historyTrain.add(res);
            editor.putString("train", historyTrain.toString());
            editor.commit();
        }
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

    public static ArrayList<Integer> getHistoryTrain() {
        return historyTrain;
    }

    public static ArrayList<Integer> getHistoryTest() {
        return historyTest;
    }
}
