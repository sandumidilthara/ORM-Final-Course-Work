package lk.ijse.Bo;

import lk.ijse.Bo.Custom.Impl.*;

public class BoFactory {


    private static BoFactory boFactory;


    public static BoFactory getBoFactory() {
        return boFactory == null ? boFactory = new BoFactory() : boFactory;
    }

    public enum BoType {
        USER, PROGRAM, STUDENT, STUDENT_COURSE,PAYMENT
    }

    public SuperBo getBoType(BoType boType) {
        switch (boType) {
            case USER:
                return new UserBoImpl();
            case PROGRAM:
                return new ProgramBoImpl();
            case STUDENT:
                return new StudentBoImpl();
            case STUDENT_COURSE:
                return new StudentCourseBoImpl();
            case PAYMENT:
                return new StudentRegisterBoImpl();
            default:
                return null;
        }
    }
}

