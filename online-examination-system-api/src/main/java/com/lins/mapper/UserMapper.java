package com.lins.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.entity.*;

import java.util.List;

public interface UserMapper {

    Admin adminLogin(Login login);

    Teacher teacherLogin(Login login);

    Student studentLogin(Login login);

    List<Institute> getInstitute();

    List<Major> getMajorsByInstituteId(String instituteId);

    List<Clazz> getClassesByMajorId(String majorId);

    int studentRegister(Student student);

    List<Student> getStudentInfos(Page<Student> studentPage, String studentId, String studentName);

    int updateStudentInfo(Student student);

    int deleteStudentByStudentId(Integer studentId);

    int resetTeacherPassword(Integer teacherId);

    int resetStudentPassword(Integer studentId);

    int deleteTeacherById(Integer teacherId);

    int updateTeacher(Teacher teacher);

    List<Teacher> getTeachers(Page<Teacher> teacherPage, String teacherId, String teacherName);

    int addTeacher(Teacher teacher);

    int updateTeacherPwd(Integer id,String password);

    int updateStudentPwd(Integer id,String password);

    Integer getSameStudentId(Integer studentId);

    Integer getSameTeacherId(Integer userId);
}
