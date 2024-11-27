package lk.ijse.Dao.Custom;

import lk.ijse.Dao.CrudDao;

import lk.ijse.Entity.Student_Register;

import java.io.IOException;

public interface StudentRegisterDao extends CrudDao<Student_Register> {
    String getCurrentId() throws IOException;

}
