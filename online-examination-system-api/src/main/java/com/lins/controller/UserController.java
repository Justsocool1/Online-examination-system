package com.lins.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.common.ResponseResult;
import com.lins.entity.*;
import com.lins.service.UserService;
import com.lins.utils.TokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult UserLogin(@RequestBody Login login) {
        if (null != login) {
            String role = login.getRole();
            login.setUserId(login.getUserId().trim());
            login.setPassword(login.getPassword().trim());
            Map<String,Object> map = new HashMap<>();
            String token = "";
            switch (role) {
                case "0":
                    Admin adminRes = (Admin) userService.userLogin(login);
                    if (null != adminRes) {
                        token = TokenUtil.sign(login);
                        map.put("token",token);
                        map.put("userInfo",adminRes);
                        return new ResponseResult(200, true, "", map);
                    }
                    break;
                case "1":
                    Teacher teacherRes = (Teacher) userService.userLogin(login);
                    if (null != teacherRes) {
                        token = TokenUtil.sign(login);
                        map.put("token",token);
                        map.put("userInfo",teacherRes);
                        return new ResponseResult(200, true, "", map);
                    }
                    break;
                case "2":
                    Student studentRes = (Student) userService.userLogin(login);
                    if (null != studentRes) {
                        token = TokenUtil.sign(login);
                        map.put("token",token);
                        map.put("userInfo",studentRes);
                        return new ResponseResult(200, true, "", map);
                    }
                    break;
                default:

            }
        }
        return new ResponseResult(404, false, "用户不存在", null);
    }

    @GetMapping("/getInstitutes")
    public ResponseResult getInstitutes() {
        List<Institute> instituteList = userService.getInstitute();
        if (!instituteList.isEmpty()) {
            return new ResponseResult(200, true, "", instituteList);
        }
        return new ResponseResult(404, false, "获取学院列表失败！", null);
    }

    @GetMapping("/getMajorsByInstituteId")
    public ResponseResult getMajorsByInstituteId(@RequestParam("instituteId") String instituteId) {
        List<Major> majorList = userService.getMajorsByInstituteId(instituteId);
        if (!majorList.isEmpty()) {
            return new ResponseResult(200, true, "", majorList);
        }
        return new ResponseResult(404, false, "获取专业失败!", null);
    }

    @GetMapping("/getClassesByMajorId")
    public ResponseResult getClazzByInstituteId(@RequestParam("majorId") String majorId) {
        List<Clazz> ClazzList = userService.getClassesByMajorId(majorId);
        if (!ClazzList.isEmpty()) {
            return new ResponseResult(200, true, "", ClazzList);
        }
        return new ResponseResult(404, false, "获取班级失败!", null);
    }

    @PostMapping("/register")
    public ResponseResult studentRegister(@RequestBody Student student) {

        String sex = student.getSex();
        if (sex == null) {
            return new ResponseResult(404, false, "前端传值失败!!", null);
        }
        if ("1".equals(sex)) {
            student.setSex("男");
        } else {
            student.setSex("女");
        }
        boolean success = userService.studentRegister(student);
        if (success) {
            return new ResponseResult(200, true, "", success);
        }
        return new ResponseResult(404, false, "获取班级失败!", null);
    }

    @GetMapping("/getStudentInfos/{page}/{size}/{studentId}/{studentName}")
    public ResponseResult getStudentInfos(@PathVariable("page") Integer page,
                                          @PathVariable("size") Integer size,
                                          @PathVariable("studentId") String studentId,
                                          @PathVariable("studentName") String studentName) {
        Page<Student> studentPage = new Page<>(page, size);
        IPage<Student> studentIPage = userService.getStudentInfos(studentPage, studentId, studentName);
        return new ResponseResult(200, true, "", studentIPage);
    }

    @PutMapping("/updateStudentInfo")
    public ResponseResult updateStudentInfo(@RequestBody Student student) {

        boolean result = userService.updateStudentInfo(student);

        if (result) {
            return new ResponseResult(200, true, "", result);
        }
        return new ResponseResult(404, false, "更新失败", null);

    }

    @PutMapping("/updateTeacher")
    public ResponseResult updateTeacher(@RequestBody Teacher teacher) {

        boolean result = userService.updateTeacher(teacher);

        if (result) {
            return new ResponseResult(200, true, "", result);
        }
        return new ResponseResult(404, false, "更新失败", null);

    }

    @DeleteMapping("/deleteStudentByStudentId")
    public ResponseResult deleteStudentByStudentId(@RequestBody Map<String, Integer> map) {
        Integer studentId = map.get("studentId");
        boolean result = userService.deleteStudentByStudentId(studentId);
        if (result) {
            return new ResponseResult(200, result, "", result);
        }
        return new ResponseResult(200, false, "", null);
    }

    @DeleteMapping("/deleteTeacherById")
    public ResponseResult deleteTeacherById(@RequestBody Map<String, Integer> map) {
        Integer teacherId = map.get("teacherId");
        boolean result = userService.deleteTeacherById(teacherId);
        if (result) {
            return new ResponseResult(200, result, "", result);
        }
        return new ResponseResult(200, false, "", null);
    }

    @PutMapping("resetPassword")
    public ResponseResult resetPassword(@RequestBody Map<String, Integer> map) {
        Integer role = map.get("role");
        Integer id = map.get("id");
        boolean result;
        switch (role) {
            case 1:
                result = userService.resetTeacherPassword(id);
                if (result) {
                    return new ResponseResult(200, true, "", null);
                }
                break;
            case 2:
                result = userService.resetStudentPassword(id);
                if (result) {
                    return new ResponseResult(200, true, "", null);
                }
                break;
            default:

        }
        return new ResponseResult(404, false, "重置失败", null);
    }

    @GetMapping("/getTeachers/{page}/{size}/{teacherId}/{teacherName}")
    public ResponseResult getTeachers(@PathVariable("page") Integer page,
                                      @PathVariable("size") Integer size,
                                      @PathVariable("teacherId") String teacherId,
                                      @PathVariable("teacherName") String teacherName) {
        Page<Teacher> teacherPage = new Page<>(page, size);
        IPage<Teacher> teacherIPage = userService.getTeachers(teacherPage, teacherId, teacherName);

        return new ResponseResult(200, true, "", teacherIPage);

    }

    @PostMapping("/addTeacher")
    public ResponseResult addTeacher(@RequestBody Teacher teacher) {

        boolean result = userService.addTeacher(teacher);

        if (result) {
            return new ResponseResult(200, result, "", result);
        }
        return new ResponseResult(404, false, "添加教师失败", null);
    }

    @PutMapping("/updatePassword")
    public ResponseResult updatePassword(@RequestBody Map<String, Object> map) {
        String json = JSON.toJSONString(map.get("id"));
        Integer studentId = JSON.parseObject(json, Integer.class);

        json = JSON.toJSONString(map.get("role"));
        Integer role = JSON.parseObject(json, Integer.class);

        json = JSON.toJSONString(map.get("password"));
        String password = JSON.parseObject(json, String.class);

        boolean result = userService.updatePassword(studentId, role, password);
        if (result) {
            return new ResponseResult(200, true, "", null);
        }
        return new ResponseResult(404, false, "", null);
    }

    @GetMapping("/getSameId")
    public ResponseResult getSameStudentId(@RequestParam("userId") Integer id,@RequestParam("role") Integer role) {
        Integer userId = userService.getSameStudentId(id,role);
        if (userId == null) {
            return new ResponseResult(200, true, "", userId);
        }
        return new ResponseResult(404, false, "存在相同学号", null);
    }
}


