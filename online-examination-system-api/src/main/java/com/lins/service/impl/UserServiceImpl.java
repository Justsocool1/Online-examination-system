package com.lins.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.entity.*;
import com.lins.mapper.UserMapper;
import com.lins.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Object userLogin(Login login) {

        switch (login.getRole()) {
            case "0":
                return userMapper.adminLogin(login);
            case "1":
                return userMapper.teacherLogin(login);
            case "2":
                return userMapper.studentLogin(login);
            default:

        }
        return null;
    }

    @Override
    public List<Institute> getInstitute() {
        List<Institute> instituteList = userMapper.getInstitute();
        return instituteList;
    }

    @Override
    public List<Major> getMajorsByInstituteId(String instituteId) {
        List<Major> majorList = userMapper.getMajorsByInstituteId(instituteId);
        return majorList;
    }

    @Override
    public List<Clazz> getClassesByMajorId(String majorId) {
        List<Clazz> clazzList = userMapper.getClassesByMajorId(majorId);
        return clazzList;
    }

    @Override
    public boolean studentRegister(Student student) {
        int result = userMapper.studentRegister(student);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public IPage<Student> getStudentInfos(Page<Student> studentPage, String studentId, String studentName) {
        if ("null".equals(studentId)) {
            studentId = null;
        }
        if ("null".equals(studentName)) {
            studentName = null;
        }
        List<Student> students = userMapper.getStudentInfos(studentPage, studentId, studentName);
        studentPage.setRecords(students);
        return studentPage;
    }

    @Override
    public boolean updateStudentInfo(Student student) {

        int result = userMapper.updateStudentInfo(student);

        if (result == 1) {
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteStudentByStudentId(Integer studentId) {
        int result = userMapper.deleteStudentByStudentId(studentId);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean resetTeacherPassword(Integer teacherId) {
        int result = userMapper.resetTeacherPassword(teacherId);

        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean resetStudentPassword(Integer studentId) {
        int result = userMapper.resetStudentPassword(studentId);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteTeacherById(Integer teacherId) {

        int result = userMapper.deleteTeacherById(teacherId);
        if (result == 1) {
            return true;
        }

        return false;
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        int result = userMapper.updateTeacher(teacher);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public IPage<Teacher> getTeachers(Page<Teacher> teacherPage, String teacherId, String teacherName) {

        if ("null".equals(teacherId)) {
            teacherId = null;
        }
        if ("null".equals(teacherName)) {
            teacherName = null;
        }

        List<Teacher> teachers = userMapper.getTeachers(teacherPage, teacherId, teacherName);

        teacherPage.setRecords(teachers);

        return teacherPage;
    }

    @Override
    public boolean addTeacher(Teacher teacher) {

        int result = userMapper.addTeacher(teacher);

        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePassword(Integer id, Integer role, String password) {
        int result;
        switch (role) {
            case 1:
                result = userMapper.updateTeacherPwd(id, password);
                if (result == 1) {
                    return true;
                }
                break;
            case 2:
                result = userMapper.updateStudentPwd(id, password);
                if (result == 1) {
                    return true;
                }
                break;
        }

        return false;
    }

    @Override
    public Integer getSameStudentId(Integer userId,Integer role) {
        Integer id = 0;
        switch (role){
            case 1:
                id = userMapper.getSameTeacherId(userId);
                break;
            case 2:
                id =  userMapper.getSameStudentId(userId);
                break;
        }


        return id;
    }
}
