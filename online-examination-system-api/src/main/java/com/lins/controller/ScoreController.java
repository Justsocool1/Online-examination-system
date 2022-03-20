package com.lins.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.common.ResponseResult;
import com.lins.entity.Score;
import com.lins.service.ExamService;
import com.lins.service.ScoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/score")
public class ScoreController {


    @Resource
    private ScoreService scoreService;

    @Resource
    private ExamService examService;

    @PostMapping("/commitScore")
    public ResponseResult commitScore(@RequestBody Map<String, String> map) {
        String id = map.get("id");
        String teqId = map.get("teqId");
        String studentId = map.get("studentId");
        String examCode = map.get("examCode");
        String subject = map.get("subject");
        String allScore = map.get("allScore");
        String answerDate = map.get("answerDate");
        boolean result = scoreService.commitScore(examCode, studentId, subject, allScore, answerDate);
        boolean rs = examService.deleteStudentExamInfo(id, teqId);
        if (result && rs) {
            return new ResponseResult(200, result, "", result);
        }
        return new ResponseResult(404, false, "失败!", null);
    }

    @GetMapping("getScores/{page}/{size}/{studentId}")
    public ResponseResult getScoresByPage(@PathVariable("page") Integer page,
                                          @PathVariable("size") Integer size,
                                          @PathVariable("studentId") String studentId) {
        Page<Score> scorePage = new Page<>(page, size);
        IPage<Score> scoreIPage = scoreService.getScoresPage(scorePage, studentId);
        return new ResponseResult(200, true, "", scoreIPage);
    }

    @GetMapping("/getStudentScoreByStudentId")
    public ResponseResult getStudentScoreByStudentId(@RequestParam("studentId") Integer studentId) {

        List<Score> scores = scoreService.getStudentScoreByStudentId(studentId);
        if (scores.isEmpty()) {
            return new ResponseResult(404, false, "获取成绩为空！", scores);
        }
        return new ResponseResult(200, true, "", scores);

    }

    @GetMapping("/getExamsScoreByExamCode")
    public ResponseResult getExamsScoreByExamCode(@RequestParam("examCode") Integer examCode) {
        List<Score> scores = scoreService.getExamsScoreByExamCode(examCode);
        if (scores.isEmpty()) {
            return new ResponseResult(404, false, "获取成绩为空！", scores);
        }
        return new ResponseResult(200, true, "", scores);
    }
}

