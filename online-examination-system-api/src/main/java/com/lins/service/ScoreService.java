package com.lins.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.entity.Score;

import java.util.List;

public interface ScoreService {
    boolean commitScore(String examCode, String studentId, String subject, String allScore, String answerDate);

    IPage<Score> getScoresPage(Page<Score> scorePage,String studentId);

    List<Score> getStudentScoreByStudentId(Integer studentId);

    List<Score> getExamsScoreByExamCode(Integer examCode);
}
