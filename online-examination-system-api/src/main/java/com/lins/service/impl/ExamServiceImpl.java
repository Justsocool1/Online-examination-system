package com.lins.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.common.EssayQuestionToTeacherCommon;
import com.lins.entity.EssayQuestionToTeacher;
import com.lins.entity.ExamManage;
import com.lins.entity.Institute;
import com.lins.entity.TransferEssayQuestion;
import com.lins.mapper.ExamMapper;
import com.lins.mapper.UserMapper;
import com.lins.service.ExamService;
import com.lins.vo.EssayQuestionToTeacherVO;
import com.lins.vo.TransferEssayQuestionVO;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Log4j
public class ExamServiceImpl implements ExamService {

    @Resource
    private ExamMapper examMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public List<ExamManage> getExamsByExamCode(Integer examCode) {
        List<ExamManage> examList = examMapper.getExamsByExamCode(examCode);
        return examList;
    }

    @Override
    public IPage<ExamManage> getExamsLikeName(Page page, String examName) {
        examName = "%" + examName + "%";
        List<ExamManage> examList = examMapper.getExamsLikeName(examName);
        return page.setRecords(examList);
    }

    @Override
    public IPage<ExamManage> getExamsByPage(Page<ExamManage> examManagePage) {

        List<ExamManage> examManageList = examMapper.getExamsByPage(examManagePage);
        return examManagePage.setRecords(examManageList);


    }

    @Override
    public boolean pushToTeacher(EssayQuestionToTeacherCommon eToT) {
        List<TransferEssayQuestion> essayQuestions = eToT.getEssayQuestions();
        Iterator<TransferEssayQuestion> iterator = essayQuestions.iterator();
        EssayQuestionToTeacher essayQuestionToTeacher = new EssayQuestionToTeacher();
        TransferEssayQuestion transferEssayQuestion = new TransferEssayQuestion();

        essayQuestionToTeacher.setExamCode(eToT.getExamCode());
        essayQuestionToTeacher.setAnswerDate(eToT.getAnswerDate());
        essayQuestionToTeacher.setStudentId(eToT.getStudentId());
        essayQuestionToTeacher.setStudentName(eToT.getStudentName());
        essayQuestionToTeacher.setSubject(eToT.getSubject());
        essayQuestionToTeacher.setStartTime(eToT.getStartTime());
        essayQuestionToTeacher.setEndTime(eToT.getEndTime());
        essayQuestionToTeacher.setNoEssayScore(eToT.getNoEssayScore());
        String teqId = UUID.randomUUID().toString().replace("-", "");
        essayQuestionToTeacher.setTeqId(teqId);
        int result = examMapper.pushToTeacher(essayQuestionToTeacher);

        while (iterator.hasNext()) {
            TransferEssayQuestion essayQuestion = iterator.next();
            transferEssayQuestion.setTeqId(teqId);
            transferEssayQuestion.setQuestionId(essayQuestion.getQuestionId());
            transferEssayQuestion.setStuAnswer(essayQuestion.getStuAnswer());
            int rs = examMapper.insertStuExam(transferEssayQuestion);

            if (result != 1 && rs != 1) {
                return false;
            }
        }
        return true;

    }

    @Override
    public IPage<EssayQuestionToTeacherVO> getNoEssayScoreExamInfo(Page<EssayQuestionToTeacherVO> page) {
        List<EssayQuestionToTeacherVO> essayQuestionToTeacherVOList = examMapper.getNoEssayScoreExamInfo(page);
        page.setRecords(essayQuestionToTeacherVOList);
        return page;
    }

    @Override
    public List<TransferEssayQuestionVO> getEssays(String teqId) {
        List<TransferEssayQuestionVO> transferEssayQuestionVOList = examMapper.getEssays(teqId);
        return transferEssayQuestionVOList;
    }

    @Override
    public boolean deleteStudentExamInfo(String id, String teqId) {

        int result = examMapper.deleteEQT(id);
        int rs = examMapper.deleteTEQ(teqId);
        if (result == 1 && rs == 1) {
            return true;
        }
        return false;
    }

    @Override
    public int getLastExamPaperId() {
        int result = examMapper.getLastExamPaperId();
        return result;
    }

    @Override
    public boolean addExam(ExamManage examManage) {
        int result = examMapper.addExam(examManage);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<Integer> getChoiceQuestionIdBySubject(String subject, Integer choiceQuestionNumber) {

        List<Integer> choiceQuestionIds = examMapper.getChoiceQuestionIdBySubject(subject, choiceQuestionNumber);

        return choiceQuestionIds;
    }

    @Override
    public List<Integer> getFillQuestionIdBySubject(String subject, Integer fillQuestionNumber) {

        List<Integer> fillQuestionIds = examMapper.getFillQuestionIdBySubject(subject, fillQuestionNumber);

        return fillQuestionIds;
    }

    @Override
    public List<Integer> getJudgeQuestionIdBySubject(String subject, Integer judgeQuestionNumber) {

        List<Integer> judgeQuestionIds = examMapper.getJudgeQuestionIdBySubject(subject, judgeQuestionNumber);

        return judgeQuestionIds;
    }

    @Override
    public List<Integer> getEssayQuestionIdBySubject(String subject, Integer essayQuestionNumber) {

        List<Integer> essayQuestionIds = examMapper.getEssayQuestionIdBySubject(subject, essayQuestionNumber);

        return essayQuestionIds;

    }

    @Override
    public boolean deleteExamByExamCode(Integer examCode) {

        int result = examMapper.deleteExamByExamCode(examCode);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Integer getPaperIdByExamCode(Integer examCode) {

       Integer paperId = examMapper.getPaperIdByExamCode(examCode);

        return paperId;
    }

    @Override
    public boolean updateExam(ExamManage examManage) {

        int result = examMapper.updateExam(examManage);

        if (result == 1) {
            return true;
        }

        return false;
    }
}
