package club.looli.onlineexam.admin.service;

import club.looli.onlineexam.admin.entity.Subject;

import java.util.List;
import java.util.Map;

/**
 * 学科管理业务接口
 */
public interface SubjectService {
    /**
     * 增加一条学科记录
     * @param subject
     * @return
     */
    int add(Subject subject);

    /**
     * 编辑一条学科记录
     * @param subject
     * @return
     */
    int edit(Subject subject);

    /**
     * 根据条件查询符合条件的结果
     * @param map
     * @return
     */
    List<Subject> findAllBySearch(Map<String, Object> map);

    /**
     * 根据id删除学科记录
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 获取查询结果总数
     * @param name
     * @return
     */
    int findCount(String name);

    /**
     * 根据学科名获取学科信息
     * @param name
     * @return
     */
    Subject findByName(String name);

    /**
     * 获取所有学科信息
     * @return
     */
    List<Subject> findAll();

    String findById(Integer subjectId);
}
