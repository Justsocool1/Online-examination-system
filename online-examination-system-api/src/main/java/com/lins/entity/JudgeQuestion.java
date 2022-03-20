package com.lins.entity;

import lombok.Data;

@Data
public class JudgeQuestion {
    private String questionId;
    private String subject;
    private String question;
    private String answer;
    private String analysis;
    private String score;
    private String level;
    private String section;

}
