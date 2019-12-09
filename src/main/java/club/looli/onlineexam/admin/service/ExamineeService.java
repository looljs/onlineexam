package club.looli.onlineexam.admin.service;

import club.looli.onlineexam.admin.entity.Examinee;

import java.util.List;
import java.util.Map;

/**
 * 考生管理业务接口
 */
public interface ExamineeService {
    /**
     * 增加一条考生记录
     * @param examinee
     * @return
     */
    int add(Examinee examinee);

    /**
     * 编辑一条考生记录
     * @param examinee
     * @return
     */
    int edit(Examinee examinee);

    /**
     * 根据条件查询符合条件的结果
     * @param map
     * @return
     */
    List<Examinee> findAllBySearch(Map<String, Object> map);

    /**
     * 根据id删除考生记录
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
     * 根据考生名获取考生信息
     * @param name
     * @return
     */
    Examinee findByName(String name);
    /**
     * 获取所有考生息
     * @return
     */
    List<Examinee> findAll();

}
