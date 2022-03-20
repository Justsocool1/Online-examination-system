package com.lins.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.entity.EssayQuestionToTeacher;
import com.lins.entity.ExamManage;
import com.lins.entity.TransferEssayQuestion;
import com.lins.vo.EssayQuestionToTeacherVO;
import com.lins.vo.TransferEssayQuestionVO;

import java.util.List;

public interface ExamMapper {
    List<ExamManage> getExamsByExamCode(Integer examCode);

    List<ExamManage> getExamsLikeName(String examName);

    List<ExamManage> getExamsByPage(Page<ExamManage> examManagePage);

    Integer pushToTeacher(EssayQuestionToTeacher essayQuestionToTeacher);

    List<EssayQuestionToTeacherVO> getNoEssayScoreExamInfo(Page<EssayQuestionToTeacherVO> page);

    int insertStuExam(TransferEssayQuestion transferEssayQuestion);

    List<TransferEssayQuestionVO> getEssays(String teqId);

    int deleteEQT(String id);

    int deleteTEQ(String teqId);

    int getLastExamPaperId();

    int addExam(ExamManage examManage);


    List<Integer> getChoiceQuestionIdBySubject(String subject, Integer number);

    List<Integer> getFillQuestionIdBySubject(String subject, Integer number);

    List<Integer> getJudgeQuestionIdBySubject(String subject, Integer number);

    List<Integer> getEssayQuestionIdBySubject(String subject, Integer number);

    int deleteExamByExamCode(Integer examCode);

    int updateExam(ExamManage examManage);

    Integer getPaperIdByExamCode(Integer examCode);
}
