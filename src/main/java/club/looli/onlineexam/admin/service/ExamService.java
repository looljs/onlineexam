package club.looli.onlineexam.admin.service;

import club.looli.onlineexam.admin.entity.Exam;
import club.looli.onlineexam.admin.entity.Examinee;

import java.util.List;
import java.util.Map;

/**
 * 考试管理业务接口
 */
public interface ExamService {
    /**
     * 增加一条考试记录
     * @param exam
     * @return
     */
    int add(Exam exam);

    /**
     * 编辑一条考试记录
     * @param exam
     * @return
     */
    int edit(Exam exam);

    /**
     * 根据条件查询符合条件的结果
     * @param map
     * @return
     */
    List<Exam> findAllBySearch(Map<String, Object> map);

    /**
     * 根据id删除考试记录
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
     * 根据考试名获取考试信息
     * @param name
     * @return
     */
    Exam findByName(String name);

    /**
     * 获取所有考试信息
     * @return
     */
    List<Exam> findAll();
}
