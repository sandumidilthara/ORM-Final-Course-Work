package lk.ijse.Dto;

import lk.ijse.Entity.Studnet_Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentRegisterDto {
    private String pay_id;
    private String pay_date;
    private double pay_amount;
    private String status;
    private double balance_amount;
    private Studnet_Course student_course;
}
