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
            "(#{subjectId},#{name},#{startTime},#{endTime},#{avaliableTime},#{questionNum},#{totalScore},#{passScore},#{singleQuestionNum},#{muiltQuestionNum},#{chargeQuestionNum},#{createTime},#{passNum})")
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
    List<Exam> findAllBySearch(Map<String, Object> map);

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
    int findCount(Map<String, Object> map);

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

    /**
     * 查询进行中的考试
     * @param map
     * @return
     */
    @Select({
            "<script>",
            "select id,subjectId,name,startTime,endTime,avaliableTime,questionNum,totalScore,passScore,singleQuestionNum,muiltQuestionNum,chargeQuestionNum,paperNum,examedNum,passNum,createTime from exam",
            "<where>",
            "<if test='subjectId != null'>",
            "AND subjectId = #{subjectId}",
            "</if>",
            "<if test=\"time != null\">\n" +
                    "\t\t\tand startTime &lt;= #{time}  \n" +
                    "\t\t</if>\n" +
                    "\t\t<if test=\"time != null\">\n" +
                    "\t\t\tand endTime &gt;= #{time}  \n" +
                    "\t\t</if>",
            "</where>",
            "</script>"
    })
    List<Exam> findExamsInProgressBySearch(Map<String, Object> map);

    /**
     * 获取历史中的考试
     * @param name
     * @return
     */
    @Select("SELECT a.id AS examId,b.id AS examineeId ,c.id AS examPaperId,a.`name`,a.avaliableTime,c.useTime,c.startExamTime,c.endExamTime,c.score FROM  \n" +
            "exam as a , examinee as b ,exampaper AS c\n" +
            "WHERE a.id = c.examId AND c.examineeId = b.id and b.`name` = #{name}")
    @Results({
            @Result(column = "name", property = "name"),
            @Result(column = "avaliableTime", property = "avaliableTime"),
            @Result(column = "useTime", property = "useTime"),
            @Result(column = "startExamTime", property = "startExamTime"),
            @Result(column = "score", property = "score")
    }
    )
    List<Map<String, Object>> findHistoryExamBySearch(String name);

    /**
     * 我的考试
     * @param map
     * @return
     */
    @Select({
            "<script>",
            "select id,subjectId,name,startTime,endTime,avaliableTime,questionNum,totalScore,passScore,singleQuestionNum,muiltQuestionNum,chargeQuestionNum,paperNum,examedNum,passNum,createTime from exam",
            "<where>",
            "<if test='subjectId != null'>",
            "AND subjectId = #{subjectId}",
            "</if>",
            "<if test='name != null'>",
            "AND name like #{name}",
            "</if>",
            "<if test=\"time != null\">\n" +
                    "\t\t\tand startTime &lt;= #{time}  \n" +
                    "\t\t</if>\n" +
                    "\t\t<if test=\"time != null\">\n" +
                    "\t\t\tand endTime &gt;= #{time}  \n" +
                    "\t\t</if>",
            "</where>",
            "order by createTime desc",
            "limit #{start},#{size}",
            "</script>"
    })
    List<Exam> findExamsBySearch(Map<String, Object> map);

    /**
     * 历史考试
     * @param map
     * @return
     */

    @Select({
            "<script>",
            "select a.`name`,b.`status`,a.avaliableTime,b.useTime,b.startExamTime,b.endExamTime,b.score,a.totalScore,a.passScore,a.id as examId,b.id as examPaperId\n" +
                    "from exam a,exampaper b\n" +
                    "WHERE \n" +
                    "a.id = b.examId\n" +
                    "and b.examineeId= #{examineeId} and b.`status` = 1 ",
            "<if test='name != null'>",
            "AND a.`name` like #{name}",
            "</if>",
            "order by a.`createTime` desc",
            "limit #{start},#{size}",
            "</script>"
    })
    @Results({
            @Result(column = "name", property = "name"),
            @Result(column = "status", property = "status"),
            @Result(column = "avaliableTime", property = "avaliableTime"),
            @Result(column = "useTime", property = "useTime"),
            @Result(column = "startTime", property = "startTime"),
            @Result(column = "endTime", property = "endTime"),
            @Result(column = "score", property = "score"),
            @Result(column = "totalScore", property = "totalScore"),
            @Result(column = "passScore", property = "passScore"),
            @Result(column = "examId", property = "examId"),
            @Result(column = "examPaperId", property = "examPaperId")
    }
    )
    List<Map<String, Object>> findHistoryExamsBySearch(Map<String, Object> map);

    @Select("select * from exam where id = #{examId}")
    Exam findById(Integer examId);

    /**
     * 修改考试试卷数
     * @param exam
     * @return
     */
    @Update("update exam set  paperNum = #{paperNum},examedNum = #{examedNum},passNum = #{passNum} where id = #{id}")
    int updateExam(Exam exam);

}
