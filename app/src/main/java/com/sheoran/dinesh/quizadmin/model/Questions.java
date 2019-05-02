package com.sheoran.dinesh.quizadmin.model;


import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sheoran.dinesh.quizadmin.BR;

import java.io.Serializable;

public class Questions extends BaseObservable implements Serializable{

    private String id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;
    private String categoryName;

    public Questions() {//Need for firebase

    }

    public Questions(String question, String option1, String option2, String option3, String option4, String rightAnswer, String categoryName) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.rightAnswer = rightAnswer;
        this.categoryName = categoryName;
    }

    @Bindable
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
        notifyPropertyChanged(BR.question);
    }

    @Bindable
    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
        notifyPropertyChanged(BR.option1);
    }

    @Bindable
    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
        notifyPropertyChanged(BR.option2);
    }

    @Bindable
    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
        notifyPropertyChanged(BR.option3);
    }

    @Bindable
    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
        notifyPropertyChanged(BR.option4);
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
        notifyPropertyChanged(BR.rightAnswer);
    }

    @Bindable
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        notifyPropertyChanged(BR.categoryName);
    }

}
