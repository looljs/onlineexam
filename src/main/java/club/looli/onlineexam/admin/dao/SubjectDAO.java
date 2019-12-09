package club.looli.onlineexam.admin.dao;

import club.looli.onlineexam.admin.entity.Subject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 学科管理持久化接口
 */
@Mapper
@Repository
public interface SubjectDAO {
    /**
     * 增加一条学科记录
     * @param subject
     * @return
     */
    @Insert("insert into subject (name,remark) values (#{name},#{remark})")
    int add(Subject subject);

    /**
     * 编辑一条学科记录
     * @param subject
     * @return
     */
    @Update("update subject set name=#{name},remark=#{remark} where id = #{id}")
    int edit(Subject subject);

    /**
     * 根据条件查询符合条件的结果
     * @param map
     * @return
     */
    @Select("select id,name,remark from subject where name like #{name} limit #{start},#{size}")
    List<Subject> findAllBySearch(Map<String, Object> map);

    /**
     * 根据id删除学科记录
     * @param id
     * @return
     */
    @Delete("delete from subject where id = #{id}")
    int delete(Integer id);

    /**
     * 获取查询结果总数
     * @param name
     * @return
     */
    @Select("select count(id) from subject where name like #{name}")
    int findCount(String name);

    /**
     * 根据学科名获取学科信息
     * @param name
     * @return
     */
    @Select("select id,name,remark from subject where name = #{name}")
    Subject findByName(String name);

    /**
     * 获取所有学科信息
     * @return
     */
    @Select("select id,name from subject")
    List<Subject> findAll();

    /**
     * 获取当前用户学科名
     * @param subjectId
     * @return
     */
    @Select("select name from subject where id = #{subjectId}")
    String findById(Integer subjectId);
}
