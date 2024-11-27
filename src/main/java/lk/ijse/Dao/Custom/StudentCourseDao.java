package lk.ijse.Dao.Custom;

import lk.ijse.Dao.CrudDao;
import lk.ijse.Entity.Studnet_Course;


public interface StudentCourseDao  extends CrudDao<Studnet_Course> {


    Studnet_Course getStudentCourseById(Long value);

}

