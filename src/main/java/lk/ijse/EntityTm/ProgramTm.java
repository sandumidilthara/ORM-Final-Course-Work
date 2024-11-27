package lk.ijse.EntityTm;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProgramTm {



    private String course_id;
    private String course_name;
    private String duration;
    private double course_fee;
    private double register_fee;
}