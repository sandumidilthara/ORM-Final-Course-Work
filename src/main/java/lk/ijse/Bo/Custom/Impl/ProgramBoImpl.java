package lk.ijse.Bo.Custom.Impl;

import lk.ijse.Bo.Custom.ProgramBo;

import lk.ijse.Dao.Custom.ProgramDao;
import lk.ijse.Dao.DaoFactory;
import lk.ijse.Dto.ProgramDto;
import lk.ijse.Entity.Program;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramBoImpl implements ProgramBo {


    ProgramDao courseDao = (ProgramDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.COURSE);

    @Override
    public boolean save(ProgramDto courseDto) throws IOException {
        Program course = new Program(
                courseDto.getCourse_id(),
                courseDto.getCourse_name(),
                courseDto.getDuration(),
                courseDto.getCourse_fee(),
                 courseDto.getRegister_fee(),
                null

        );
        return courseDao.save(course);
    }

    @Override
    public boolean update(ProgramDto courseDto) throws IOException {
        Program course = new Program(
                courseDto.getCourse_id(),
                courseDto.getCourse_name(),
                courseDto.getDuration(),
                courseDto.getCourse_fee(),
                courseDto.getRegister_fee(),
                null


        );
        return courseDao.update(course);
    }

    @Override
    public boolean delete(String id) throws IOException {
        return courseDao.delete(id);
    }

    @Override
    public ProgramDto getCourse(String id) throws IOException {
        Program byId = courseDao.findById(id);
        ProgramDto courseDto = new ProgramDto();
        courseDto.setCourse_id(byId.getCourse_id());
        courseDto.setCourse_name(byId.getCourse_name());
        courseDto.setDuration(byId.getDuration());
        courseDto.setCourse_fee(byId.getCourse_fee());
        courseDto.setRegister_fee(byId.getRegister_fee());
        return courseDto;
    }

    @Override
    public List<Program> getCourseList() throws IOException {
        List<Program> courseList = new ArrayList<>();
        List<Program> courses = courseDao.getAll();
        for (Program course : courses) {
            courseList.add(new Program(
                    course.getCourse_id(),
                    course.getCourse_name(),
                    course.getDuration(),
                    course.getCourse_fee(),
                     course.getRegister_fee(),
                    null

            ));
        }
        return courseList;
    }






}
