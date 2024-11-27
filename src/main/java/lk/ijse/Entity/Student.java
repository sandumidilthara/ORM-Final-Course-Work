package lk.ijse.Entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "student")

public class Student {

    @Id
    private String stu_id;
    private String stu_name;
    private String stu_address;

    private String stu_phone;
    private int status;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Studnet_Course> studentCourses;


}
