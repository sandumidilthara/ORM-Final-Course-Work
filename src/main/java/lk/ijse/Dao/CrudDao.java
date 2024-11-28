package lk.ijse.Dao;

import java.io.IOException;
import java.util.List;

public interface CrudDao<T> extends SuperDao {
     boolean save(T object) throws IOException;
      boolean update(T object) throws IOException;
    boolean delete(String id) throws IOException;
     T findById(String id) throws IOException;
      List<T> getAll() throws IOException;
}
