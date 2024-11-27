package lk.ijse.Bo.Custom;

import lk.ijse.Bo.SuperBo;
import lk.ijse.Dto.ProgramDto;
import lk.ijse.Entity.Program;

import java.io.IOException;
import java.util.List;

public interface ProgramBo extends SuperBo {


    public boolean save(ProgramDto courseDto) throws IOException;
    public boolean update(ProgramDto courseDto) throws IOException;
    public boolean delete(String id) throws IOException;
    public ProgramDto getCourse(String id) throws IOException;
    public List<Program> getCourseList() throws IOException;
}
