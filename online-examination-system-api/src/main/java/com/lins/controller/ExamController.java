package com.lins.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.common.EssayQuestionToTeacherCommon;
import com.lins.common.ResponseResult;
import com.lins.entity.*;
import com.lins.service.ExamService;
import com.lins.service.PaperSerivce;
import com.lins.service.UserService;
import com.lins.vo.EssayQuestionToTeacherVO;
import com.lins.vo.TransferEssayQuestionVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/exam")
public class ExamController {

    @Resource
    private ExamService examService;

    @Resource
    private UserService userService;

    @Resource
    private PaperSerivce paperSerivce;

//    @GetMapping("/getExams/{examName}")
//    public ResponseResult getExams(@PathVariable("examName") String examName) {
//        Page<ExamManage> examPage = new Page<>(1,6);
//        if ("null".equals(examName) | "".equals(examName.trim())) {
//            IPage<ExamManage> examList = examService.getExamsByPage(examPage);
//            if (!examList.getRecords().isEmpty()) {
//                return new ResponseResult(200, true, "", examList);
//            } else {
//                return new ResponseResult(404, false, "获取试卷失败！", null);
//            }
//        } else {
//            IPage<ExamManage> examList = examService.getExamsLikeName(examPage,examName);
//            if (!examList.getRecords().isEmpty()) {
//                return new ResponseResult(200, true, "", examList);
//            } else {
//                return new ResponseResult(404, false, "获取试卷失败！", null);
//            }
//        }
//    }
//
//    @GetMapping("/getExams/{page}/{size}")
//    public ResponseResult getExamByPage(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
//        Page<ExamManage> examManagePage = new Page<>(page, size);
//        IPage<ExamManage> examManageIPage = examService.getExamsByPage(examManagePage);
//        return new ResponseResult(200,true,"请求成功！",examManageIPage);
//    }

    @GetMapping("/getExams/{page}/{size}/{examName}")
    public ResponseResult getExams(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @PathVariable("examName") String examName) {
        Page<ExamManage> examPage;
        if ("null".equals(examName) | "".equals(examName.trim())) {
            examPage = new Page<>(page, size);
            IPage<ExamManage> examList = examService.getExamsByPage(examPage);
            if (!examList.getRecords().isEmpty()) {
                return new ResponseResult(200, true, "", examList);
            }
            return new ResponseResult(404, false, "获取试卷失败！", null);

        } else {
            examPage = new Page<>(1, 6);
            IPage<ExamManage> examList = examService.getExamsLikeName(examPage, examName);
            if (!examList.getRecords().isEmpty()) {
                return new ResponseResult(200, true, "", examList);
            }
            return new ResponseResult(404, false, "获取试卷失败！", null);

        }
    }

    @GetMapping("/getExamInfoByExamCode")
    public ResponseResult getExamInfoByExamCode(@RequestParam("examCode") Integer examCode) {

        List<ExamManage> examManageList = examService.getExamsByExamCode(examCode);
        if (!examManageList.isEmpty()) {
            return new ResponseResult(200, true, "", examManageList);
        }

        return new ResponseResult(404, false, "获取试卷信息失败！", null);
    }

    @PostMapping("/pushToTeacher")
    public ResponseResult pushToTeacher(@RequestBody EssayQuestionToTeacherCommon eToT) {
        boolean result;

        if (null != eToT) {
            result = examService.pushToTeacher(eToT);
            int code;
            if (result) {
                code = 200;
            } else {
                code = 404;
            }
            return new ResponseResult(code, result, "", null);
        }
        return new ResponseResult(500, false, "失败", null);
    }

    @GetMapping("/getNoEssayScoreExamInfo/{page}/{size}")
    public ResponseResult getNoEssayScoreExamInfo(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<EssayQuestionToTeacherVO> objectPage = new Page<>(page, size);
        IPage<EssayQuestionToTeacherVO> stuExams = examService.getNoEssayScoreExamInfo(objectPage);
        if (stuExams.getRecords().isEmpty()) {
            return new ResponseResult(404, false, "失败！", null);
        }
        return new ResponseResult(200, true, "", stuExams);
    }

    @GetMapping("/getEssays")
    public ResponseResult getEssays(@RequestParam("teqId") String teqId) {
        List<TransferEssayQuestionVO> transferEssayQuestionVOList = examService.getEssays(teqId);
        if (transferEssayQuestionVOList.isEmpty()) {
            return new ResponseResult(404, false, "失败！", null);
        }
        return new ResponseResult(200, true, "", transferEssayQuestionVOList);
    }

    @PostMapping("/addExam")
    public ResponseResult addExam(@RequestBody ExamManage examManage) {
        int id = examService.getLastExamPaperId() + 1;
        String paperId = String.valueOf(id);
        examManage.setPaperId(paperId);
        boolean result = examService.addExam(examManage);
        if (result) {
            return new ResponseResult(200, result, "", result);
        }
        return new ResponseResult(404, false, "失败!", null);
    }

    @GetMapping("/getInstitutes")
    public ResponseResult getInstitutes() {

        List<Institute> institutes = userService.getInstitute();
        if (institutes.isEmpty()) {
            return new ResponseResult(404, false, "失败！", null);
        }
        return new ResponseResult(200, true, "", institutes);
    }

    @GetMapping("/getMajorsByInstituteId")
    public ResponseResult getMajors(@RequestParam("instituteId") String instituteId) {
        List<Major> majors = userService.getMajorsByInstituteId(instituteId);
        if (majors.isEmpty()) {
            return new ResponseResult(404, false, "失败！", null);
        }
        return new ResponseResult(200, true, "", majors);
    }

    @PutMapping("/updateExam")
    public ResponseResult updateExam(@RequestBody ExamManage examManage) {

        boolean result = examService.updateExam(examManage);
        if (result) {
            return new ResponseResult(200, true, "", null);
        }
        return new ResponseResult(404, false, "更新失败", null);

    }

    @DeleteMapping("/deleteExam")
    public ResponseResult deleteExam(@RequestBody Map<String, Integer> map) {

        Integer examCode = map.get("examCode");

        Integer paperId = examService.getPaperIdByExamCode(examCode);

        boolean rs = paperSerivce.deletePaperByPaperId(paperId);

        boolean result = examService.deleteExamByExamCode(examCode);



        if (result && rs) {
            return new ResponseResult(200, true, "", null);
        }
        return new ResponseResult(404, false, "删除失败或者数据库中没有数据可以删除", null);


    }

}
