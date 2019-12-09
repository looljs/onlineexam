package club.looli.onlineexam.admin.service;

import club.looli.onlineexam.admin.entity.ExamPaper;
import club.looli.onlineexam.admin.entity.ExamPaperAnswerDetails;
import club.looli.onlineexam.admin.entity.Question;

import java.util.List;
import java.util.Map;

/**
 * 试卷答题详情管理业务接口
 */
public interface ExamPaperAnswerDetailsService {
    /**
     * 增加一条试卷答题详情记录
     * @param examPaperAnswerDetails
     * @return
     */
    int add(ExamPaperAnswerDetails examPaperAnswerDetails);

    /**
     * 编辑一条试卷答题详情记录
     * @param examPaperAnswerDetails
     * @return
     */
    int edit(ExamPaperAnswerDetails examPaperAnswerDetails);

    /**
     * 根据条件查询符合条件的结果
     * @param map
     * @return
     */
    List<ExamPaperAnswerDetails> findAllBySearch(Map<String, Object> map);

    /**
     * 根据id删除试卷答题详情记录
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 获取查询结果总数
     * @param map
     * @return
     */
    int findCount(Map<String, Object> map);

    /**
     * 查看试题
     * @param map
     * @return
     */
    //SELECT a.title,a.score,a.attrA,a.attrB,a.attrC,a.attrD,a.questionType FROM question a,examPaperAnswerDetails b WHERE b.examId = 2 AND b.examineeId=6 AND b.questionId = a.id
    List<Question> findAllBySearch2(Map<String, Object> map);


    /**
     * 提交答案
     * @param examPaperAnswerDetails
     * @return
     */
    int submitAnswer(ExamPaperAnswerDetails examPaperAnswerDetails);


    List<Question> findAllBySearch3(Map<String, Object> queryMap);

    /**
     * 查看试题
     * @param map
     * @return
     */
    List<Map<String,Object>> findAllBySearch4(Map<String, Object> map);
}
