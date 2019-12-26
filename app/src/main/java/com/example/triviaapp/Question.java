package com.example.triviaapp;

import java.util.ArrayList;

public class Question {

    private String question;
    private String correct_answer;
    private String incorrect_answer1;
    private String incorrect_answer2;
    private String incorrect_answer3;

    public Question(String question, String correct_answer, String incorrect_answer1) {
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answer1 = incorrect_answer1;
    }

    public Question(String question, String correct_answer, String incorrect_answer1, String incorrect_answer2, String incorrect_answer3) {
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answer1 = incorrect_answer1;
        this.incorrect_answer2 = incorrect_answer2;
        this.incorrect_answer3 = incorrect_answer3;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getIncorrect_answer1() {
        return incorrect_answer1;
    }

    public void setIncorrect_answer1(String incorrect_answer1) {
        this.incorrect_answer1 = incorrect_answer1;
    }

    public String getIncorrect_answer2() {
        return incorrect_answer2;
    }

    public void setIncorrect_answer2(String incorrect_answer2) {
        this.incorrect_answer2 = incorrect_answer2;
    }

    public String getIncorrect_answer3() {
        return incorrect_answer3;
    }

    public void setIncorrect_answer3(String incorrect_answer3) {
        this.incorrect_answer3 = incorrect_answer3;
    }
}
