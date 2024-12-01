package com.krisi.literature12.manager;

import com.krisi.literature12.products.Product;

import java.util.ArrayList;

public class TestQuestion {

    private String question = null;
    private ArrayList<String> answers = new ArrayList<>();
    private int correctAns = 0;
    private Product prod = null;
    private QuestionType type = null;

    public TestQuestion(int correctAns, Product prod, QuestionType type) {
        this.correctAns = correctAns;
        this.prod = prod;
        this.type = type;
    }

    public TestQuestion(String question, ArrayList<String> answers, int correctAns, Product prod, QuestionType type) {
        this.question = question;
        this.answers = answers;
        this.correctAns = correctAns;
        this.prod = prod;
        this.type = type;
    }

    public String getAnswer(int i) {
        return answers.get(i);
    }
    public void addAnswer(String ans) {
        answers.add(ans);
    }
    public void addAnswer(int id, String ans) {
        answers.add(id, ans);
    }

    public boolean containsAnswer(String ans){
        return answers.contains(ans);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(int correctAns) {
        this.correctAns = correctAns;
    }

    public Product getProd() {
        return prod;
    }

    public void setProd(Product prod) {
        this.prod = prod;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }
}
