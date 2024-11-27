package lk.ijse.Bo.Custom;

import lk.ijse.Entity.Studnet_Course;

import java.io.IOException;
import java.util.List;

public interface StudentCourseBo {

    public List<Studnet_Course> getStudentCourseList() throws IOException;
}
