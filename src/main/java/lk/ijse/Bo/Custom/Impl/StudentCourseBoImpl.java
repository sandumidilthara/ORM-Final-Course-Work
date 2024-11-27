package lk.ijse.Bo.Custom.Impl;

import lk.ijse.Bo.Custom.StudentCourseBo;
import lk.ijse.Bo.SuperBo;
import lk.ijse.Dao.Custom.StudentCourseDao;
import lk.ijse.Dao.DaoFactory;
import lk.ijse.Entity.Studnet_Course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentCourseBoImpl implements StudentCourseBo, SuperBo {
    StudentCourseDao studentCourseDao = (StudentCourseDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.STUDENT_COURSE);

    public List<Studnet_Course> getStudentCourseList() throws IOException {
        List<Studnet_Course> studentCourseList = new ArrayList<>();
        List<Studnet_Course> studentCourses = studentCourseDao.getAll();
        for (Studnet_Course studentCourse : studentCourses) {
            studentCourseList.add(new Studnet_Course(
                    studentCourse.getStudent_course_id(),
                    studentCourse.getRegistration_date(),
                    studentCourse.getStudent(),
                    studentCourse.getCourse(),
                    new ArrayList<>()
            ));
        }
        return studentCourseList;
    }
}
