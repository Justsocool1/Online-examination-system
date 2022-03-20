package com.lins.entity;

import lombok.Data;

@Data
public class ChoiceQuestion {
    private String questionId;
    private String subject;
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String rightAnswer;
    private String analysis;
    private String score;
    private String section;
    private String level;

}
