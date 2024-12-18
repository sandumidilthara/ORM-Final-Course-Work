package lk.ijse.Bo.Custom.Impl;


import lk.ijse.Bo.Custom.StudentBo;

import lk.ijse.Dao.Custom.StudentDao;
import lk.ijse.Dao.DaoFactory;

import lk.ijse.Dto.StudentDto;

import lk.ijse.Entity.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentBoImpl implements StudentBo {


    StudentDao studentDao = (StudentDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.STUDENT);
    @Override
    public boolean save(StudentDto courseDto) throws IOException {
       Student course = new Student(
                courseDto.getStu_id(),
                courseDto.getStu_name(),
                courseDto.getStu_address(),
                courseDto.getStu_phone(),
               1,
               null

        );
        return studentDao.save(course);
    }

    @Override
    public boolean update(StudentDto studentDto) throws IOException {
        Student student = new Student(
                studentDto.getStu_id(),
                studentDto.getStu_name(),
                studentDto.getStu_address(),
                studentDto.getStu_phone(),
                1,
                null


        );
        return studentDao.update(student);
    }

    @Override
    public boolean delete(String id) throws IOException {
        return studentDao.delete(id);
    }



    @Override
    public List<Student> getStudentList() throws IOException {
        List<Student> studentList = new ArrayList<>();
        List<Student> students = studentDao.getAll();
        for (Student student : students) {
            studentList.add(new Student(
                    student.getStu_id(),
                    student.getStu_name(),
                    student.getStu_address(),
                    student.getStu_phone(),
                    1,
                    null


            ));
        }
        return studentList;
    }
}
