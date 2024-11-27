package lk.ijse.Dao.Custom;

import lk.ijse.Dao.CrudDao;
import lk.ijse.Entity.Program;

import java.io.IOException;
import java.util.List;

public interface ProgramDao extends CrudDao<Program> {


    String getCurrentId() throws IOException;

    List<String> getCourseId();

    List<String> getCourseIds();

    Program getCourseById(String courseId);
    int getProgramCount();
}
