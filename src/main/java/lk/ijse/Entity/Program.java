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
@Table(name = "program")
public class Program {


    @Id
    private String course_id;
    private String course_name;
    private String duration;
    private double course_fee;
    private double register_fee;



    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Studnet_Course> studentCourses;

}





