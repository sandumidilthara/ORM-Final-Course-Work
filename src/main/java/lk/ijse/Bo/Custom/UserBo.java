package lk.ijse.Bo.Custom;

import lk.ijse.Bo.SuperBo;
import lk.ijse.Dto.UserDto;
import lk.ijse.Entity.User;

import java.io.IOException;
import java.util.List;

public interface UserBo extends SuperBo {

     boolean save(UserDto userDto) throws IOException;
   boolean update(UserDto userDto) throws IOException;
     boolean delete(String id) throws IOException;

     List<User> getUserList() throws IOException;


}
