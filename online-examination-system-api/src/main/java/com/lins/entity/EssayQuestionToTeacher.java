package com.lins.entity;

import lombok.Data;

@Data
public class EssayQuestionToTeacher {
    private String id;
    private String examCode;
    private String studentId;
    private String studentName;
    private String subject;
    private String noEssayScore;
    private String answerDate;
    private String startTime;
    private String endTime;
    private String teqId;


}
