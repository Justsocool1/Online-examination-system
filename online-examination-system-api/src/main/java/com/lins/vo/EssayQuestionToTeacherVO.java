package com.lins.vo;


import lombok.Data;



@Data
public class EssayQuestionToTeacherVO {
    private String id;
    private String examCode;
    private String studentId;
    private String studentName;
    private String subject;
    private String description;
    private String institute;
    private String major;
    private String grade;
    private String type;
    private String noEssayScore;
    private String answerDate;
    private String startTime;
    private String endTime;
    private String teqId;

}


