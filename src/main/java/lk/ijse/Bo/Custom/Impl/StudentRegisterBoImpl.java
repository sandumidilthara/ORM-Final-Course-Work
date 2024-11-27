package lk.ijse.Bo.Custom.Impl;

import lk.ijse.Bo.Custom.StudentRegisterBo;
import lk.ijse.Bo.SuperBo;
import lk.ijse.Dao.Custom.StudentRegisterDao;
import lk.ijse.Dao.DaoFactory;
import lk.ijse.Dto.StudentRegisterDto;
import lk.ijse.Entity.Student_Register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentRegisterBoImpl implements StudentRegisterBo {


     StudentRegisterDao paymentDao = (StudentRegisterDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.PAYMENT);



    @Override
    public boolean savePayment(StudentRegisterDto paymentDto) throws IOException {
        Student_Register payment = new Student_Register(
                paymentDto.getPay_id(),
                paymentDto.getPay_date(),
                paymentDto.getPay_amount(),
                paymentDto.getStatus(),
                paymentDto.getPay_amount(),
                paymentDto.getBalance_amount(),
                paymentDto.getStudent_course()
        );
        return paymentDao.save(payment);
    }

    @Override
    public List<Student_Register> getPaymentList() throws IOException {
        List<Student_Register> paymentList = new ArrayList<>();
        List<Student_Register> payments = paymentDao.getAll();
        for (Student_Register payment : payments) {
            paymentList.add(new Student_Register(
                    payment.getPay_id(),
                    payment.getPay_date(),
                    payment.getPay_amount(),
                    payment.getStatus(),
                    payment.getUpfront_amount(),
                    payment.getBalance_amount(),
                    payment.getStudent_course()
            ));
        }
        return paymentList;
    }


}
