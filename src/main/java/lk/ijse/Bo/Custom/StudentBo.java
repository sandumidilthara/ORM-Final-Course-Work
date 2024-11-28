package lk.ijse.Bo.Custom;

import lk.ijse.Bo.SuperBo;
import lk.ijse.Dto.StudentDto;
import lk.ijse.Entity.Student;

import java.io.IOException;
import java.util.List;

public interface StudentBo extends SuperBo {

     boolean save(StudentDto studentDto) throws IOException;
     boolean update(StudentDto studentDto) throws IOException;
      boolean delete(String id) throws IOException;

     List<Student> getStudentList() throws IOException;

}
