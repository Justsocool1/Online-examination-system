package com.lins.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.entity.Score;
import com.lins.mapper.ScoreMapper;
import com.lins.service.ScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Resource
    private ScoreMapper scoreMapper;

    @Override
    public boolean commitScore(String examCode, String studentId, String subject, String allScore, String answerDate) {
        int result = scoreMapper.commitScore(examCode,studentId,subject,allScore,answerDate);
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public IPage<Score> getScoresPage(Page<Score> scorePage,String studentId) {

      List<Score> scoreList =  scoreMapper.getScores(scorePage,studentId);
        scorePage.setRecords(scoreList);
        return scorePage;
    }

    @Override
    public List<Score> getStudentScoreByStudentId(Integer studentId) {

        List<Score> scores = scoreMapper.getStudentScoreByStudentId(studentId);

        return scores;
    }

    @Override
    public List<Score> getExamsScoreByExamCode(Integer examCode) {

        List<Score> scores = scoreMapper.getExamsScoreByExamCode(examCode);

        return scores;
    }
}
