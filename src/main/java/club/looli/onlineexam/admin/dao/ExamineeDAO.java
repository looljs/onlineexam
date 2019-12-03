package club.looli.onlineexam.admin.dao;

import club.looli.onlineexam.admin.entity.Examinee;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 考生管理持久化接口
 */
@Repository
@Mapper
public interface ExamineeDAO {
    /**
     * 增加一条考生记录
     * @param examinee
     * @return
     */
    @Insert("insert into examinee (id,subjectId,name,password,trueName,tel,createTime) values (#{id},#{subjectId},#{name},#{password},#{trueName},#{tel},#{createTime})")
    int add(Examinee examinee);

    /**
     * 编辑一条考生记录
     * @param examinee
     * @return
     */
    @Update("update examinee set subjectId=#{subjectId},name=#{name},password=#{password},trueName=#{trueName},tel=#{tel} where id = #{id}")
    int edit(Examinee examinee);

    /**
     * 根据条件查询符合条件的结果
     * @param map
     * @return
     */
    @Select({
            "<script>",
            "select id,subjectId,name,password,trueName,tel,createTime from examinee",
            "<where>",
            "<if test='name != null'>",
            "AND name like #{name}",
            "</if>",
            "<if test='subjectId != null'>",
            "AND subjectId = #{subjectId}",
            "</if>",
            "</where>",
            "limit #{start},#{size}",
            "</script>"
    })
    List<Examinee> findAllBySearch(Map<String,Object> map);

    /**
     * 根据id删除考生记录
     * @param id
     * @return
     */
    @Delete("delete from examinee where id = #{id}")
    int delete(Integer id);

    /**
     * 获取查询结果总数
     * @param map
     * @return
     */
    @Select({
            "<script>",
            "select count(id) from examinee",
            "<where>",
            "<if test='name != null'>",
            "AND name like #{name}",
            "</if>",
            "<if test='subjectId != null'>",
            "AND subjectId = #{subjectId}",
            "</if>",
            "</where>",
            "</script>"
    })
    int findCount(Map<String,Object> map);

    /**
     * 根据考生名获取考生信息
     * @param name
     * @return
     */
    @Select("select id,subjectId,name,password,trueName,tel,createTime from examinee where name = #{name}")
    Examinee findByName(String name);
}
