package club.looli.onlineexam.admin.service.impl;

import club.looli.onlineexam.admin.dao.ExamDAO;
import club.looli.onlineexam.admin.entity.Exam;
import club.looli.onlineexam.admin.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamDAO examDAO;

    @Override
    public int add(Exam exam) {
        return examDAO.add(exam);
    }

    @Override
    public int edit(Exam exam) {
        return examDAO.edit(exam);
    }

    @Override
    public List<Exam> findAllBySearch(Map<String, Object> map) {
        return examDAO.findAllBySearch(map);
    }

    @Override
    public int delete(Integer id) {
        return examDAO.delete(id);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return examDAO.findCount(map);
    }

    @Override
    public Exam findByName(String name) {
        return examDAO.findByName(name);
    }

    @Override
    public List<Exam> findAll() {
        return examDAO.findAll();
    }
}
