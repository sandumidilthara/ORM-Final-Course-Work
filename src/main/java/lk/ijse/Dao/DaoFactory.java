package lk.ijse.Dao;

import lk.ijse.Dao.Custom.Impl.*;

public class DaoFactory {

    private static DaoFactory daoFactory;
    private DaoFactory() {}

    public static DaoFactory getDaoFactory() {
        return daoFactory == null ? daoFactory = new DaoFactory() : daoFactory;
    }
    public enum DaoType{
        USER,COURSE,STUDENT,PAYMENT,STUDENT_COURSE
    }
    public SuperDao getDaoType(DaoType daoType){
        switch (daoType){
            case USER:
                return new UserDaoImpl();
            case COURSE:
                return new ProgramDaoImpl();
            case STUDENT:
                return new StudentDaoImpl();
            case PAYMENT:
                return new StudentRegisterDaoImpl();
            case STUDENT_COURSE:
                return new StudentCourseDaoImpl();
            default:
                return null;
        }
    }



    }

