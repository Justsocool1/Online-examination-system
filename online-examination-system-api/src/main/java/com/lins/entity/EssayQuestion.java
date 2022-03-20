package com.lins.entity;

import lombok.Data;

@Data
public class EssayQuestion {
    private String questionId;
    private String subject;
    private String question;
    private String answer;
    private String analysis;
    private String score;
    private String section;
    private String level;

}
