package club.looli.onlineexam.admin.service;

import club.looli.onlineexam.admin.entity.Question;

import java.util.List;
import java.util.Map;

/**
 * 试题管理业务接口
 */
public interface QuestionService {
    /**
     * 增加一条试题记录
     * @param question
     * @return
     */
    int add(Question question);

    /**
     * 编辑一条试题记录
     * @param question
     * @return
     */
    int edit(Question question);

    /**
     * 根据条件查询符合条件的结果
     * @param map
     * @return
     */
    List<Question> findAllBySearch(Map<String,Object> map);

    /**
     * 根据id删除试题记录
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 获取查询结果总数
     * @param map
     * @return
     */
    int findCount(Map<String,Object> map);

    /**
     * 根据试题名获取试题信息
     * @param title
     * @return
     */
    Question findByTitle(String title);
}
