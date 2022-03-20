package com.lins.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.common.ResponseResult;
import com.lins.entity.*;

import com.lins.service.ExamService;
import com.lins.service.PaperSerivce;
import com.lins.vo.PaperVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/paper")
public class PaperController {


    @Resource
    private PaperSerivce paperSerivce;

    @Resource
    private ExamService examService;

    @GetMapping("/getPaperById")
    public ResponseResult getPaperById(@RequestParam("paperId") Integer paperId) {

        Map<Integer, List<?>> map = new HashMap<>();
        List<ChoiceQuestion> choiceQuestionList = paperSerivce.getChoiceQuestionByPaperId(paperId);
        List<FillQuestion> fillQuestionList = paperSerivce.getFillQuestionByPaperId(paperId);
        List<JudgeQuestion> judgeQuestionList = paperSerivce.getJudgeQuestionByPaperId(paperId);
        List<EssayQuestion> essayQuestionList = paperSerivce.getEssayQuestionByPaperId(paperId);
        map.put(1, choiceQuestionList);
        map.put(2, fillQuestionList);
        map.put(3, judgeQuestionList);
        map.put(4, essayQuestionList);

        return new ResponseResult(200, true, "", map);


    }

    @PostMapping("/addOneChoiceQuestion")
    public ResponseResult addOneChoiceQuestion(@RequestBody Map<String, Object> map) {


        String json = JSON.toJSONString(map.get("question"));
        ChoiceQuestion question = JSON.parseObject(json, ChoiceQuestion.class);
        Integer paperId = JSON.parseObject(map.get("paperId").toString(), Integer.class);

        boolean result = paperSerivce.addChoiceQuestion(paperId, question);

        if (result) {
            return new ResponseResult(200, true, "", result);
        }

        return new ResponseResult(404, false, "", null);
    }

    @PostMapping("/addJudgeQuestion")
    public ResponseResult addJudgeQuestion(@RequestBody Map<String, Object> map) {

        String json = JSON.toJSONString(map.get("question"));
        JudgeQuestion question = JSON.parseObject(json, JudgeQuestion.class);
        Integer paperId = JSON.parseObject(map.get("paperId").toString(), Integer.class);

        boolean result = paperSerivce.addJudgeQuestion(paperId, question);

        if (result) {
            return new ResponseResult(200, true, "", result);
        }

        return new ResponseResult(404, false, "", null);
    }

    @PostMapping("/addFillQuestion")
    public ResponseResult addFillQuestion(@RequestBody Map<String, Object> map) {

        String json = JSON.toJSONString(map.get("question"));
        FillQuestion question = JSON.parseObject(json, FillQuestion.class);
        Integer paperId = JSON.parseObject(map.get("paperId").toString(), Integer.class);

        boolean result = paperSerivce.addFillQuestion(paperId, question);

        if (result) {
            return new ResponseResult(200, true, "", result);
        }

        return new ResponseResult(404, false, "", null);
    }

    @PostMapping("/addEssayQuestion")
    public ResponseResult addEssayQuestion(@RequestBody Map<String, ?> map) {
        String json = JSON.toJSONString(map.get("question"));
        EssayQuestion question = JSON.parseObject(json, EssayQuestion.class);

        Integer paperId = JSON.parseObject(map.get("paperId").toString(), Integer.class);
        boolean result = paperSerivce.addEssayQuestion(paperId, question);

        if (result) {
            return new ResponseResult(200, true, "", result);
        }

        return new ResponseResult(404, false, "", null);
    }

    @PostMapping("/randomPaper")
    public ResponseResult randomPaper(@RequestBody Map<String, Object> map) {

        Integer choiceQuestionNumber = JSON.parseObject(map.get("changeNumber").toString(), Integer.class);
        Integer fillQuestionNumber = JSON.parseObject(map.get("fillNumber").toString(), Integer.class);
        Integer judgeQuestionNumber = JSON.parseObject(map.get("judgeNumber").toString(), Integer.class);
        Integer essayQuestionNumber = JSON.parseObject(map.get("essayNumber").toString(), Integer.class);
        String paperId = JSON.parseObject(map.get("paperId").toString(), String.class);
        String subject = (String) map.get("subject");
//       String subject = JSON.toJSONString(map.get("subject"));
//        subject = JSON.parseObject(subject,String.class);
        PaperManager paper;
        boolean result;
        //选择题
        List<Integer> choiceQuestionIds = examService.getChoiceQuestionIdBySubject(subject, choiceQuestionNumber);
        if (!choiceQuestionIds.isEmpty()) {
            for (Integer choiceQuestionId : choiceQuestionIds) {
                String id = String.valueOf(choiceQuestionId);
                paper = new PaperManager(paperId, "1", id);
                result = paperSerivce.addPaper(paper);
                if (!result) {
                    return new ResponseResult(404, false, "选择题失败", null);
                }
            }
        } else {
            return new ResponseResult(404, false, "选择题失败", null);
        }

        // 填空题
        List<Integer> fillQuestionIds = examService.getFillQuestionIdBySubject(subject, fillQuestionNumber);
        if (!fillQuestionIds.isEmpty()) {
            for (Integer fillQuestionId : fillQuestionIds) {
                String id = String.valueOf(fillQuestionId);
                paper = new PaperManager(paperId, "2", id);
                result = paperSerivce.addPaper(paper);
                if (!result) {
                    return new ResponseResult(404, false, "选择题失败", null);
                }
            }
        } else {
            return new ResponseResult(404, false, "选择题失败", null);
        }

        // 判断题
        List<Integer> judgeQuestionIds = examService.getJudgeQuestionIdBySubject(subject, judgeQuestionNumber);
        if (!judgeQuestionIds.isEmpty()) {
            for (Integer judgeQuestionId : judgeQuestionIds) {
                String id = String.valueOf(judgeQuestionId);
                paper = new PaperManager(paperId, "3", id);
                result = paperSerivce.addPaper(paper);
                if (!result) {
                    return new ResponseResult(404, false, "选择题失败", null);
                }
            }
        } else {
            return new ResponseResult(404, false, "选择题失败", null);

        }

        // 问答题
        List<Integer> essayQuestionIds = examService.getEssayQuestionIdBySubject(subject, essayQuestionNumber);
        if (!essayQuestionIds.isEmpty()) {
            for (Integer essayQuestionId : essayQuestionIds) {
                String id = String.valueOf(essayQuestionId);
                paper = new PaperManager(paperId, "4", id);
                result = paperSerivce.addPaper(paper);
                if (!result) {
                    return new ResponseResult(404, false, "选择题失败", null);
                }
            }
        } else {
            return new ResponseResult(404, false, "选择题失败", null);
        }


        return new ResponseResult(200, true, "", null);
    }

    @GetMapping("/getPapers/{page}/{size}/{examName}/{section}/{type}/{level}")
    public ResponseResult getPapers(@PathVariable("page") Integer page,
                                    @PathVariable("size") Integer size,
                                    @PathVariable("examName") String examName,
                                    @PathVariable("section") String section,
                                    @PathVariable("type") String questionType,
                                    @PathVariable("level") String level) {

        Page<PaperVO> paperPage = new Page<>(page, size);
        IPage<PaperVO> papers = paperSerivce.getPapersByCondition(paperPage, examName, section, questionType, level);

        return new ResponseResult(200, true, "", papers);

    }

}
