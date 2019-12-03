package club.looli.onlineexam.admin.service.impl;

import club.looli.onlineexam.admin.dao.ExamineeDAO;
import club.looli.onlineexam.admin.entity.Examinee;
import club.looli.onlineexam.admin.service.ExamineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 考生管理业务实现
 */
@Service
public class ExamineeServiceImpl implements ExamineeService {

    @Autowired
    private ExamineeDAO examineeDAO;

    @Override
    public int add(Examinee examinee) {
        return examineeDAO.add(examinee);
    }

    @Override
    public int edit(Examinee examinee) {
        return examineeDAO.edit(examinee);
    }

    @Override
    public List<Examinee> findAllBySearch(Map<String, Object> map) {
        return examineeDAO.findAllBySearch(map);
    }

    @Override
    public int delete(Integer id) {
        return examineeDAO.delete(id);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return examineeDAO.findCount(map);
    }

    @Override
    public Examinee findByName(String name) {
        return examineeDAO.findByName(name);
    }
}
