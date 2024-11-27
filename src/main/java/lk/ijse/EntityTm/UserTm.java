package lk.ijse.EntityTm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTm {
    private String user_id;
    private String username;

    private String user_phone;
    private String user_role;
}
