package com.lins.entity;

import lombok.Data;

@Data
public class Score {
    private String scoreId;
    private String examCode;
    private String studentId;
    private String subject;
    private String ptScore;
    private String etScore;
    private String score;
    private String answerDate;
}
