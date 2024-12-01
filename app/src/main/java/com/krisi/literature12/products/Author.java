package com.krisi.literature12.products;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private String name;
    private List<Product> products;

    public Author(String n){
        name = n;
        products = new ArrayList<>();
    }

    public void addProduct(Product p){
        products.add(p);
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }
}
