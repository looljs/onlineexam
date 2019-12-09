package club.looli.onlineexam.admin.service;

import club.looli.onlineexam.admin.entity.ExamPaper;

import java.util.List;
import java.util.Map;

/**
 * 试卷管理业务接口
 */
public interface ExamPaperService {
    /**
     * 增加一条试卷记录
     * @param examPaper
     * @return
     */
    int add(ExamPaper examPaper);

    /**
     * 编辑一条试卷记录
     * @param examPaper
     * @return
     */
    int edit(ExamPaper examPaper);

    /**
     * 根据条件查询符合条件的结果
     * @param map
     * @return
     */
    List<ExamPaper> findAllBySearch(Map<String, Object> map);

    /**
     * 根据id删除试卷记录
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
     * 获取试卷
     * @param queryMap
     * @return
     */
    ExamPaper find(Map<String, Object> queryMap);



    /**
     * 跟新试卷开始时间
     * @param find
     * @return
     */
    int updateStartExamTime(ExamPaper find);

    /**
     * 提交试卷
     * @param examPaper
     * @return
     */
    int submitPaper(ExamPaper examPaper);

    /**
     * 获取考试信息
     * @param examId
     * @return
     */
    List<Map<String, Object>> getExamStats(Integer examId);
}
