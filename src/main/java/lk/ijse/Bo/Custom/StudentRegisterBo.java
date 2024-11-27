package lk.ijse.Bo.Custom;

import lk.ijse.Bo.SuperBo;
import lk.ijse.Dto.StudentRegisterDto;
import lk.ijse.Entity.Student_Register;

import java.io.IOException;
import java.util.List;

public interface StudentRegisterBo extends SuperBo {

    boolean savePayment(  StudentRegisterDto paymentDto) throws IOException;
    List<Student_Register> getPaymentList() throws IOException;
}
