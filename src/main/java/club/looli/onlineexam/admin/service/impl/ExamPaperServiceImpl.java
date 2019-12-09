package club.looli.onlineexam.admin.service.impl;

import club.looli.onlineexam.admin.dao.ExamPaperDAO;
import club.looli.onlineexam.admin.entity.ExamPaper;
import club.looli.onlineexam.admin.service.ExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 试卷管理业务接口实现
 */
@Service
public class ExamPaperServiceImpl implements ExamPaperService {

    @Autowired
    private ExamPaperDAO examPaperDAO;


    @Override
    public int add(ExamPaper examPaper) {
        return examPaperDAO.add(examPaper);
    }

    @Override
    public int edit(ExamPaper examPaper) {
        return examPaperDAO.edit(examPaper);
    }

    @Override
    public List<ExamPaper> findAllBySearch(Map<String, Object> map) {
        return examPaperDAO.findAllBySearch(map);
    }

    @Override
    public int delete(Integer id) {
        return examPaperDAO.delete(id);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return examPaperDAO.findCount(map);
    }

    @Override
    public ExamPaper find(Map<String, Object> queryMap) {
        return examPaperDAO.find(queryMap);
    }

    @Override
    public int updateStartExamTime(ExamPaper find) {
        return examPaperDAO.updateStartExamTime(find);
    }

    @Override
    public int submitPaper(ExamPaper examPaper) {
        return examPaperDAO.submitPaper(examPaper);
    }

    @Override
    public List<Map<String, Object>> getExamStats(Integer examId) {
        return examPaperDAO.getExamStats(examId);
    }
}
