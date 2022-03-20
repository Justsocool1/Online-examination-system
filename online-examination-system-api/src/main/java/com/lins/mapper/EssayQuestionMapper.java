package com.lins.mapper;

import com.lins.entity.EssayQuestion;

import java.util.List;

public interface EssayQuestionMapper {
    List<EssayQuestion> getEssayQuestionByPaperId(Integer paperId, Integer questionType);

    int getLastQuestionId();

    int addEssayQuestion(EssayQuestion question);
}
