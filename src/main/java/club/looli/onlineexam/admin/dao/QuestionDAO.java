package club.looli.onlineexam.admin.dao;

import club.looli.onlineexam.admin.entity.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 试题管理持久化接口
 */
@Mapper
@Repository
public interface QuestionDAO {
    /**
     * 增加一条试题记录
     * @param question
     * @return
     */
    @Insert("insert into question (id,subjectId,questionType,title,score,attrA,attrB,attrC,attrD,answer,createTime) " +
            "values (#{id},#{subjectId},#{questionType},#{title},#{score},#{attrA}," +
            "#{attrB},#{attrC},#{attrD},#{answer},#{createTime})")
    int add(Question question);

    /**
     * 编辑一条试题记录
     * @param question
     * @return
     */
    @Update("update question set id=#{id},subjectId=#{subjectId},questionType=#{questionType},title=#{title},score=#{score},attrA=#{attrA}," +
            "attrB=#{attrB},attrC=#{attrC},attrD=#{attrD},answer=#{answer} where id = #{id}")
    int edit(Question question);

    /**
     * 根据条件查询符合条件的结果
     * @param map
     * @return
     */
    @Select({
            "<script>",
            "select id,subjectId,questionType,title,score,attrA,attrB,attrC,attrD,answer,createTime from question",
            "<where>",
            "<if test='questionType != null'>",
            "AND questionType = #{questionType}",
            "</if>",
            "<if test='title != null'>",
            "AND title like #{title}",
            "</if>",
            "<if test='subjectId != null'>",
            "AND subjectId = #{subjectId}",
            "</if>",
            "</where>",
            "limit #{start},#{size}",
            "</script>"
    })
    List<Question> findAllBySearch(Map<String,Object> map);

    /**
     * 根据id删除试题记录
     * @param id
     * @return
     */
    @Delete("delete from question where id = #{id}")
    int delete(Integer id);

    /**
     * 获取查询结果总数
     * @param map
     * @return
     */
    @Select({
            "<script>",
            "select count(id) from question",
            "<where>",
            "<if test='questionType != null'>",
            "AND questionType = #{questionType}",
            "</if>",
            "<if test='title != null'>",
            "AND title like #{title}",
            "</if>",
            "<if test='subjectId != null'>",
            "AND subjectId = #{subjectId}",
            "</if>",
            "</where>",
            "</script>"
    })
    int findCount(Map<String,Object> map);

    /**
     * 根据试题名获取试题信息
     * @param title
     * @return
     */
    @Select("select id,subjectId,questionType,title,score,attrA,attrB,attrC,attrD,answer,createTime from question where title = #{title}")
    Question findByTitle(String title);
}
