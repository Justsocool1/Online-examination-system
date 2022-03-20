package com.lins.mapper;

import com.lins.entity.FillQuestion;

import java.util.List;

public interface FillQuestionMapper {
    List<FillQuestion> getFillQuestionByPaperId(Integer paperId, Integer questionType);

    int addFillQuestion(FillQuestion question);

    int getLastQuestionId();
}
