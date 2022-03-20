package com.lins.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.entity.*;
import com.lins.vo.PaperVO;

import java.util.List;

public interface PaperSerivce {

    List<ChoiceQuestion> getChoiceQuestionByPaperId(Integer paperId);

    List<FillQuestion> getFillQuestionByPaperId(Integer paperId);

    List<JudgeQuestion> getJudgeQuestionByPaperId(Integer paperId);

    List<EssayQuestion> getEssayQuestionByPaperId(Integer paperId);

    boolean addChoiceQuestion(Integer paperId, ChoiceQuestion question);

    boolean addJudgeQuestion(Integer paperId, JudgeQuestion question);

    boolean addFillQuestion(Integer paperId, FillQuestion question);

    boolean addEssayQuestion(Integer paperId, EssayQuestion question);

    boolean addPaper(PaperManager paper);

    boolean deletePaperByPaperId(Integer paperId);

    IPage<PaperVO> getPapersByCondition(Page<PaperVO> paperPage, String examName, String section, String questionType, String level);
}
