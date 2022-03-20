package com.lins.entity;

import lombok.Data;

@Data
public class ExamManage {
    private String examCode;
    private String description;
    private String source;
    private String paperId;
    private String examDate;
    private String examStartTime;
    private String totalTime;
    private String grade;
    private String term;
    private String majorId;
    private String instituteId;
    private String totalScore;
    private String type;
    private String tips;

}
