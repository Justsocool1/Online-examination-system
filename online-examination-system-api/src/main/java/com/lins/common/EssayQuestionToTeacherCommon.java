package com.lins.common;

import com.lins.entity.TransferEssayQuestion;
import lombok.Data;

import java.util.List;

@Data
public class EssayQuestionToTeacherCommon {
    private String examCode;
    private String studentId;
    private String studentName;
    private String subject;
    private String noEssayScore;
    private String answerDate;
    private String startTime;
    private String endTime;
    private List<TransferEssayQuestion> EssayQuestions;

}
