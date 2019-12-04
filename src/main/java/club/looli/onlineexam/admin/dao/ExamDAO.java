package club.looli.onlineexam.admin.dao;

import club.looli.onlineexam.admin.entity.Exam;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 考试管理持久化接口
 */
@Mapper
@Repository
public interface ExamDAO {
    /**
     * 增加一条考试记录
     * @param exam
     * @return
     */
    @Insert("insert into exam (subjectId,name,startTime,endTime,avaliableTime,questionNum,totalScore,passScore,singleQuestionNum,muiltQuestionNum,chargeQuestionNum,createTime,passNum) values " +
            "(#{subjectId},#{name},#{startTime},#{endTime},#{avaliableTime},#{questionNum},#{totalScore},#{passScore},#{singleQuestionNum},#{muiltQuestionNum},#{chargeQuestionNum},#{createTime}.#{passNum})")
    int add(Exam exam);

    /**
     * 编辑一条考试记录
     * @param exam
     * @return
     */
    @Update("update exam set name = #{name},subjectId = #{subjectId},startTime = #{startTime},endTime = #{endTime},avaliableTime = #{avaliableTime},questionNum = #{questionNum},totalScore = #{totalScore},passScore = #{passScore},singleQuestionNum = #{singleQuestionNum},muiltQuestionNum = #{muiltQuestionNum},chargeQuestionNum = #{chargeQuestionNum} where id = #{id}")
    int edit(Exam exam);

    /**
     * 根据条件查询符合条件的结果
     * @param map
     * @return
     */
    @Select({
            "<script>",
            "select id,subjectId,name,startTime,endTime,avaliableTime,questionNum,totalScore,passScore,singleQuestionNum,muiltQuestionNum,chargeQuestionNum,paperNum,examedNum,passNum,createTime from exam",
            "<where>",
            "<if test='name != null'>",
            "AND name like #{name}",
            "</if>",
            "<if test='subjectId != null'>",
            "AND subjectId = #{subjectId}",
            "</if>",
            "<if test=\"startTime != null\">\n" +
                    "\t\t\tand startTime &gt;= #{startTime}  \n" +
                    "\t\t</if>\n" +
                    "\t\t<if test=\"endTime != null\">\n" +
                    "\t\t\tand endTime &lt;= #{endTime}  \n" +
                    "\t\t</if>",
            "</where>",
            "limit #{start},#{size}",
            "</script>"
    })
    List<Exam> findAllBySearch(Map<String,Object> map);

    /**
     * 根据id删除考试记录
     * @param id
     * @return
     */
    @Delete("delete from exam where id = #{id}")
    int delete(Integer id);

    /**
     * 获取查询结果总数
     * @param map
     * @return
     */
    @Select({
            "<script>",
            "select count(id) from exam",
            "<where>",
            "<if test='name != null'>",
            "AND name like #{name}",
            "</if>",
            "<if test='subjectId != null'>",
            "AND subjectId = #{subjectId}",
            "</if>",
            "<if test=\"startTime != null\">\n" +
                    "\t\t\tand startTime &gt;= #{startTime}  \n" +
                    "\t\t</if>\n" +
                    "\t\t<if test=\"endTime != null\">\n" +
                    "\t\t\tand endTime &lt;= #{endTime}  \n" +
                    "\t\t</if>",
            "</where>",
            "</script>"
    })
    int findCount(Map<String,Object> map);

    /**
     * 根据考试名获取考试信息
     * @param name
     * @return
     */
    @Select("select id,subjectId,name,startTime,endTime,avaliableTime,questionNum,totalScore,passScore,singleQuestionNum,muiltQuestionNum,chargeQuestionNum,paperNum,examedNum,passNum,createTime from exam where name = #{name}")
    Exam findByName(String name);

    /**
     * 获取所有考试信息
     * @return
     */
    @Select("select id,name from exam")
    List<Exam> findAll();
}
