package club.looli.onlineexam.admin.service.impl;

import club.looli.onlineexam.admin.dao.SubjectDAO;
import club.looli.onlineexam.admin.entity.Subject;
import club.looli.onlineexam.admin.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 学科管理业务实现
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectDAO subjectDAO;

    @Override
    public int add(Subject subject) {
        return subjectDAO.add(subject);
    }

    @Override
    public int edit(Subject subject) {
        return subjectDAO.edit(subject);
    }

    @Override
    public List<Subject> findAllBySearch(Map<String, Object> map) {
        return subjectDAO.findAllBySearch(map);
    }

    @Override
    public int delete(Integer id) {
        return subjectDAO.delete(id);
    }

    @Override
    public int findCount(String name) {
        return subjectDAO.findCount(name);
    }

    @Override
    public Subject findByName(String name) {
        return subjectDAO.findByName(name);
    }

    @Override
    public List<Subject> findAll() {
        return subjectDAO.findAll();
    }

    @Override
    public String findById(Integer subjectId) {
        return subjectDAO.findById(subjectId);
    }
}
