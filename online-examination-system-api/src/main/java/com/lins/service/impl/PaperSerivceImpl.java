package com.lins.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.entity.*;
import com.lins.mapper.*;
import com.lins.service.PaperSerivce;
import com.lins.vo.PaperVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PaperSerivceImpl implements PaperSerivce {

    @Resource
    ChoiceQuestionMapper choiceQuestionMapper;

    @Resource
    FillQuestionMapper fillQuestionMapper;

    @Resource
    JudgeQuestionMapper judgeQuestionMapper;

    @Resource
    EssayQuestionMapper essayQuestionMapper;

    @Resource
    private PaperManageMapper paperManageMapper;

    private static final Integer ChoiceQuestionType = 1;
    private static final Integer FillQuestionType = 2;
    private static final Integer JudgeQuestionType = 3;
    private static final Integer EssayQuestionType = 4;

    @Override
    public List<ChoiceQuestion> getChoiceQuestionByPaperId(Integer paperId) {
        List<ChoiceQuestion> choiceQuestionList = choiceQuestionMapper.getChoiceQuestionByPaperId(paperId, ChoiceQuestionType);
        return choiceQuestionList;
    }

    @Override
    public List<FillQuestion> getFillQuestionByPaperId(Integer paperId) {
        List<FillQuestion> fillQuestionList = fillQuestionMapper.getFillQuestionByPaperId(paperId, FillQuestionType);
        return fillQuestionList;
    }

    @Override
    public List<JudgeQuestion> getJudgeQuestionByPaperId(Integer paperId) {
        List<JudgeQuestion> judgeQuestionList = judgeQuestionMapper.getJudgeQuestionByPaperId(paperId, JudgeQuestionType);
        return judgeQuestionList;
    }

    @Override
    public List<EssayQuestion> getEssayQuestionByPaperId(Integer paperId) {
        List<EssayQuestion> essayQuestionList = essayQuestionMapper.getEssayQuestionByPaperId(paperId, EssayQuestionType);
        return essayQuestionList;
    }

    @Override
    public boolean addChoiceQuestion(Integer paperId, ChoiceQuestion question) {

        int result = choiceQuestionMapper.addChoiceQuestion(question);

        Integer questionId = choiceQuestionMapper.getLastQuestionId();

        int rs = paperManageMapper.addPaper(paperId, questionId, ChoiceQuestionType);

        if (result == 1 && rs == 1) {
            return true;
        }

        return false;
    }

    @Override
    public boolean addJudgeQuestion(Integer paperId, JudgeQuestion question) {
        int result = judgeQuestionMapper.addJudgeQuestion(question);

        Integer questionId = judgeQuestionMapper.getLastQuestionId();

        int rs = paperManageMapper.addPaper(paperId, questionId, JudgeQuestionType);

        if (result == 1 && rs == 1) {
            return true;
        }

        return false;
    }

    @Override
    public boolean addFillQuestion(Integer paperId, FillQuestion question) {
        int result = fillQuestionMapper.addFillQuestion(question);

        Integer questionId = fillQuestionMapper.getLastQuestionId();

        int rs = paperManageMapper.addPaper(paperId, questionId, FillQuestionType);

        if (result == 1 && rs == 1) {
            return true;
        }

        return false;
    }

    @Override
    public boolean addEssayQuestion(Integer paperId, EssayQuestion question) {
        int result = essayQuestionMapper.addEssayQuestion(question);

        Integer questionId = essayQuestionMapper.getLastQuestionId();

        int rs = paperManageMapper.addPaper(paperId, questionId, EssayQuestionType);

        if (result == 1 && rs == 1) {
            return true;
        }

        return false;
    }

    @Override
    public boolean addPaper(PaperManager paper) {

        int result = paperManageMapper.addPaper(Integer.valueOf(paper.getPaperId()),
                Integer.valueOf(paper.getQuestionId()),
                Integer.valueOf(paper.getQuestionType()));
        if (result == 1) {
            return true;
        }

        return false;
    }

    @Override
    public boolean deletePaperByPaperId(Integer paperId) {

        int result = paperManageMapper.deletePaperByPaperId(paperId);

        if (result != 0) {
            return true;
        }
        return false;
    }

    @Override
    public IPage<PaperVO> getPapersByCondition(Page<PaperVO> paperPage, String examName, String section, String questionType, String level) {

        if ("null".equals(questionType)){
            questionType = null;
        }
        if ("null".equals(examName)){
            examName = null;
        }
        if ("null".equals(section)){
            section = null;
        }
        if ("null".equals(level)){
            level = null;
        }
        List<PaperVO> papers = paperManageMapper.getPapersByCondition(paperPage, examName, section, questionType, level);
        paperPage.setRecords(papers);
        return paperPage;
    }
}
