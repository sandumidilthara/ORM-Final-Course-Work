package lk.ijse.Dao.Custom.Impl;

import lk.ijse.Config.FactoryConfiguration;
import lk.ijse.Dao.Custom.StudentRegisterDao;

import lk.ijse.Entity.Student_Register;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.io.IOException;
import java.util.List;

public class StudentRegisterDaoImpl implements StudentRegisterDao {
    public boolean save(Student_Register object) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Student_Register object) throws IOException {
        return false;
    }



    @Override
    public boolean delete(String id) throws IOException {
        return false;
    }

    @Override
    public  Student_Register findById(String id) throws IOException {
        return null;
    }

    @Override
    public List<Student_Register> getAll() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery query = session.createNativeQuery("SELECT * FROM payment");
        query.addEntity(Student_Register.class);
        List<Student_Register> resultList = query.getResultList();
        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public String getCurrentId() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String nextId = "";
        Object payment = session.createQuery
                ("SELECT p.pay_id  FROM Student_Register p  ORDER BY p.pay_id DESC LIMIT 1").uniqueResult();



        if (payment != null) {
            String userId = payment.toString();
            String prefix = "P";
            String[] split = userId.split(prefix);
            int idNum = Integer.parseInt(split[1]);
            nextId = prefix + String.format("%03d", ++idNum);

        } else {
            return "P001";
        }
        transaction.commit();
        session.close();
        return nextId;
    }

}
