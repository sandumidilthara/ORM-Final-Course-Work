package lk.ijse.Dao.Custom;

import lk.ijse.Dao.CrudDao;
import lk.ijse.Entity.Student;
import lk.ijse.Entity.Studnet_Course;

import java.io.IOException;

public interface StudentDao extends CrudDao<Student> {


    String getCurrentId() throws IOException;

    Student getStudentById(String text);

   void saveStudentCourseDetails(Studnet_Course studentCourse) throws IOException;
   boolean isStudentRegisteredForCourse(String stuId, String courseId) throws IOException;
    int getStudentCount();
}
