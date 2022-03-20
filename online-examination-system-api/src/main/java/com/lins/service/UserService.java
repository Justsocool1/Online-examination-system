package com.lins.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.entity.*;

import java.util.List;

public interface UserService {

    Object userLogin(Login login);

    List<Institute> getInstitute();

    List<Major> getMajorsByInstituteId(String instituteId);

    List<Clazz> getClassesByMajorId(String majorId);

    boolean studentRegister(Student student);

    IPage<Student> getStudentInfos(Page<Student> studentPage, String studentId, String studentName);

    boolean updateStudentInfo(Student student);

    boolean deleteStudentByStudentId(Integer studentId);

    boolean resetTeacherPassword(Integer teacherId);

    boolean resetStudentPassword(Integer studentId);

    boolean deleteTeacherById(Integer teacherId);

    boolean updateTeacher(Teacher teacher);

    IPage<Teacher> getTeachers(Page<Teacher> teacherPage, String teacherId, String teacherName);

    boolean addTeacher(Teacher teacher);

    boolean updatePassword(Integer id, Integer role,String password);

    Integer getSameStudentId(Integer userId,Integer role);
}

