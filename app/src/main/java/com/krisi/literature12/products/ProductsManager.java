package com.krisi.literature12.products;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductsManager {
    private static final String TAG = "PRODUCTS";
    static Context context;
    public static Map<ProductTheme, ArrayList<Product>> products = new HashMap<>();
    public static void init(Context contextm){
        context = contextm;
        JSONArray all = null;
        try {
            all = new JSONArray(loadJSONFromAsset("HOPE"));
            products.put(ProductTheme.HOPE, readThemeLevels(all));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    static ArrayList<Product> readThemeLevels(JSONArray obj) throws JSONException {
        ArrayList<Product> currLevels = new ArrayList<>();
        Log.d(TAG, "readThemeLevels: " + obj);
        for (int i = 0; i < obj.length(); i++) {
            JSONObject jo_inside = obj.getJSONObject(i);
            Log.d(TAG, "readThemeLevels: " + jo_inside);
            String myJson= jo_inside.toString();
            Product prod = new Gson().fromJson(myJson, Product.class);
            currLevels.add(prod);
        }
        return currLevels;
    }

    public static String loadJSONFromAsset(String name) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(name+".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static int productCount(ProductTheme theme){
        return products.get(theme).size();
    }

    public static Product getProduct(ProductTheme theme, int id){
        return products.get(theme).get(id);
    }
}
