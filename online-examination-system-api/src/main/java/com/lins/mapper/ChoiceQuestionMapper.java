package com.lins.mapper;

import com.lins.entity.ChoiceQuestion;

import java.util.List;

public interface ChoiceQuestionMapper {

    List<ChoiceQuestion> getChoiceQuestionByPaperId(Integer paperId, Integer questionType);

    int addChoiceQuestion(ChoiceQuestion question);

    int getLastQuestionId();
}
