package club.looli.onlineexam.admin.service.impl;

import club.looli.onlineexam.admin.dao.ExamPaperAnswerDetailsDAO;
import club.looli.onlineexam.admin.entity.ExamPaper;
import club.looli.onlineexam.admin.entity.ExamPaperAnswerDetails;
import club.looli.onlineexam.admin.entity.Question;
import club.looli.onlineexam.admin.service.ExamPaperAnswerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 试卷管理业务接口实现
 */
@Service
public class ExamPaperAnswerDetailsServiceImpl implements ExamPaperAnswerDetailsService {

    @Autowired
    private ExamPaperAnswerDetailsDAO examPaperAnswerDetailsDAO;


    @Override
    public int add(ExamPaperAnswerDetails examPaperAnswerDetails) {
        return examPaperAnswerDetailsDAO.add(examPaperAnswerDetails);
    }

    @Override
    public int edit(ExamPaperAnswerDetails examPaperAnswerDetails) {
        return examPaperAnswerDetailsDAO.edit(examPaperAnswerDetails);
    }

    @Override
    public List<ExamPaperAnswerDetails> findAllBySearch(Map<String, Object> map) {
        return examPaperAnswerDetailsDAO.findAllBySearch(map);
    }

    @Override
    public int delete(Integer id) {
        return examPaperAnswerDetailsDAO.delete(id);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return examPaperAnswerDetailsDAO.findCount(map);
    }

    @Override
    public List<Question> findAllBySearch2(Map<String, Object> map) {
        return examPaperAnswerDetailsDAO.findAllBySearch2(map);
    }

    @Override
    public int submitAnswer(ExamPaperAnswerDetails examPaperAnswerDetails) {
        return examPaperAnswerDetailsDAO.submitAnswer(examPaperAnswerDetails);
    }
    @Override
    public List<Question> findAllBySearch3(Map<String, Object> map) {
        return examPaperAnswerDetailsDAO.findAllBySearch3(map);
    }

    @Override
    public List<Map<String,Object>> findAllBySearch4(Map<String, Object> map) {
        return examPaperAnswerDetailsDAO.findAllBySearch4(map);
    }


}
