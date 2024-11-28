package lk.ijse.Bo.Custom;

import lk.ijse.Bo.SuperBo;
import lk.ijse.Dto.ProgramDto;
import lk.ijse.Entity.Program;

import java.io.IOException;
import java.util.List;

public interface ProgramBo extends SuperBo {


     boolean save(ProgramDto courseDto) throws IOException;
     boolean update(ProgramDto courseDto) throws IOException;
     boolean delete(String id) throws IOException;
     ProgramDto getCourse(String id) throws IOException;
     List<Program> getCourseList() throws IOException;
}
