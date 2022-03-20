package com.lins.mapper;


import com.lins.entity.JudgeQuestion;

import java.util.List;

public interface JudgeQuestionMapper {
    List<JudgeQuestion> getJudgeQuestionByPaperId(Integer paperId, Integer questionType);

    int addJudgeQuestion(JudgeQuestion question);

    int getLastQuestionId();
}
