package com.lins.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.entity.Score;

import java.util.List;

public interface ScoreMapper {
    int commitScore(String examCode, String studentId, String subject, String allScore, String answerDate);

    List<Score> getScores(Page page, String studentId);

    List<Score> getStudentScoreByStudentId(Integer studentId);

    List<Score> getExamsScoreByExamCode(Integer examCode);
}
