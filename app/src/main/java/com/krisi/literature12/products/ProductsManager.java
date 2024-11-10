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
            products.put(ProductTheme.HOPE, readThemeLevels(all, ProductTheme.HOPE));
            all = new JSONArray(loadJSONFromAsset("LOVE"));
            products.put(ProductTheme.LOVE, readThemeLevels(all, ProductTheme.LOVE));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    static ArrayList<Product> readThemeLevels(JSONArray obj, ProductTheme theme) throws JSONException {
        ArrayList<Product> currLevels = new ArrayList<>();
        for (int i = 0; i < obj.length(); i++) {
            JSONObject jo_inside = obj.getJSONObject(i);
            String myJson= jo_inside.toString();
            Product prod = new Gson().fromJson(myJson, Product.class);
            prod.setTheme(theme);
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
        if(!products.containsKey(theme)) return -1;
        return products.get(theme).size();
    }

    public static Product getProduct(ProductTheme theme, int id){
        if(!products.containsKey(theme)) return null;
        return products.get(theme).get(id);
    }
}
