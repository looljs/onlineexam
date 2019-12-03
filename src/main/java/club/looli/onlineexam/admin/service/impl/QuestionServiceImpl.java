package club.looli.onlineexam.admin.service.impl;

import club.looli.onlineexam.admin.dao.QuestionDAO;
import club.looli.onlineexam.admin.entity.Question;
import club.looli.onlineexam.admin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 试题业务接口实现
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDAO questionDAO;

    @Override
    public int add(Question question) {
        return questionDAO.add(question);
    }

    @Override
    public int edit(Question question) {
        return questionDAO.edit(question);
    }

    @Override
    public List<Question> findAllBySearch(Map<String, Object> map) {
        return questionDAO.findAllBySearch(map);
    }

    @Override
    public int delete(Integer id) {
        return questionDAO.delete(id);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return questionDAO.findCount(map);
    }

    @Override
    public Question findByTitle(String title) {
        return questionDAO.findByTitle(title);
    }

}
