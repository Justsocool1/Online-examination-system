package com.lins.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.common.EssayQuestionToTeacherCommon;
import com.lins.entity.ExamManage;
import com.lins.entity.Institute;
import com.lins.vo.EssayQuestionToTeacherVO;
import com.lins.vo.TransferEssayQuestionVO;

import java.util.List;
import java.util.Map;

public interface ExamService {

    List<ExamManage> getExamsByExamCode(Integer examCode);

    IPage<ExamManage> getExamsLikeName(Page page, String examName);

    IPage<ExamManage> getExamsByPage(Page<ExamManage> examManagePage);

    boolean pushToTeacher(EssayQuestionToTeacherCommon eToT);

    IPage<EssayQuestionToTeacherVO> getNoEssayScoreExamInfo(Page<EssayQuestionToTeacherVO> page);

    List<TransferEssayQuestionVO> getEssays(String teqId);

    boolean deleteStudentExamInfo(String id, String teqId);

    int getLastExamPaperId();

    boolean addExam(ExamManage examManage);


    List<Integer> getChoiceQuestionIdBySubject(String subject, Integer choiceQuestionNumber);

    List<Integer> getFillQuestionIdBySubject(String subject, Integer fillQuestionNumber);

    List<Integer> getJudgeQuestionIdBySubject(String subject, Integer judgeQuestionNumber);

    List<Integer> getEssayQuestionIdBySubject(String subject, Integer essayQuestionNumber);

    boolean updateExam(ExamManage examManage);

    boolean deleteExamByExamCode(Integer examCode);

    Integer getPaperIdByExamCode(Integer examCode);
}
