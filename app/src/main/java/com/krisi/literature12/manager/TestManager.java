package com.krisi.literature12.manager;

import android.content.Context;

import com.krisi.literature12.R;
import com.krisi.literature12.products.Product;
import com.krisi.literature12.products.ProductGenre;
import com.krisi.literature12.products.ProductsManager;

import java.util.ArrayList;

public class TestManager extends ModeManager{

    ArrayList<TestQuestion> testQuestions = new ArrayList<>();
    Context context;
    public int[] userAnswers;

    public TestManager(Context c){context = c;}

    public void initTrainingSession(ArrayList<String> products, int questions){
        super.initTrainingSession(products,questions);
        for(int i = 0 ; i < QUESTIONS_COUNT; i++){
            QuestionType type = QuestionType.getRandom();
            Product answer = allProducts.get(random.nextInt(allProducts.size()));
            int answerId = random.nextInt(4);

            boolean createdAlready = false;
            for(int j = 0; j < i; j++){
                if(testQuestions.get(j).getProd().equals(answer)
                && testQuestions.get(j).getType()==type) createdAlready = true;
            }
            if(createdAlready){ i--; continue; }

            TestQuestion question = new TestQuestion(answerId, answer, type);

            switch(type){
                case TITLE_BY_GENRE:
                    question.setQuestion(
                            context.getString(R.string.mode_test_TITLE_BY_GENRE) + " " +
                            context.getString(answer.getGenre().nameId)+"?");
                    for(int j = 0; j < 3; j++) {
                        Product prod = allProducts.get(random.nextInt(allProducts.size()));
                        if(!question.containsAnswer(prod.getTitle()) && !prod.equals(answer) && prod.getGenre()!=answer.getGenre()) {
                            question.addAnswer(prod.getTitle());
                        } else j--;
                    }
                    question.addAnswer(answerId, answer.getTitle());
                    break;
                case GENRE_BY_TITLE:
                    question.setQuestion(
                            context.getString(R.string.mode_test_GENRE_BY_TITLE) + " \"" +
                            answer.getTitle()+"\"?");
                    for(int j = 0; j < 3; j++) {
                        ProductGenre genre = ProductGenre.getRandom();
                        if(!question.containsAnswer(context.getString(genre.nameId)) && genre!=answer.getGenre()) {
                            question.addAnswer(context.getString(genre.nameId));
                        } else j--;
                    }
                    question.addAnswer(answerId, context.getString(answer.getGenre().nameId));
                    break;
                case AUTHOR_BY_TITLE:
                    question.setQuestion(
                            context.getString(R.string.mode_test_AUTHOR_BY_TITLE) + " \"" +
                            answer.getTitle() + "\"?");
                    for(int j = 0; j < 3; j++) {
                        String auth = ProductsManager.authors.get(random.nextInt(ProductsManager.authors.size()));
                        if(!question.containsAnswer(auth) && auth!=answer.getAuthorName()) {
                            question.addAnswer(auth);
                        } else j--;
                    }
                    question.addAnswer(answerId, answer.getAuthorName());
                    break;
                case TITLE_BY_AUTHOR:
                    question.setQuestion(
                            context.getString(R.string.mode_test_TITLE_BY_AUTHOR) + " " +
                            answer.getAuthorName() + "?");
                    for(int j = 0; j < 3; j++) {
                        Product prod = allProducts.get(random.nextInt(allProducts.size()));
                        if(!question.containsAnswer(prod.getTitle()) && !prod.equals(answer) && !prod.getAuthorName().equals(answer.getAuthorName())) {
                            question.addAnswer(prod.getTitle());
                        } else j--;
                    }
                    question.addAnswer(answerId, answer.getTitle());
                    break;
            }
            testQuestions.add(question);
        }
    }


    public TestQuestion getQuestionInfo(int i){
        if(i>=QUESTIONS_COUNT) return null;
        return testQuestions.get(i);
    }
    public void calculateResult(int[] answers2){
        userAnswers = answers2;
        for(int i = 0; i < QUESTIONS_COUNT; i++){
            if(userAnswers[i] != testQuestions.get(i).getCorrectAns()){
                wrongAnswerProduct(testQuestions.get(i).getProd());
                wrongAnswer();
            }
        }
    }
}
