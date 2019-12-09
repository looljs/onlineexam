package club.looli.onlineexam.admin.dao;

import club.looli.onlineexam.admin.entity.ExamPaper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 试卷管理持久化接口
 */
@Mapper
@Repository
public interface ExamPaperDAO {
    /**
     * 增加一条试卷记录
     * @param examPaper
     * @return
     */
    @Insert("insert into exampaper (examId,examineeId,status,totalScore,score,createTime,useTime) values " +
            "(#{examId},#{examineeId},#{status},#{totalScore},#{score},#{createTime},#{useTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int add(ExamPaper examPaper);

    /**
     * 编辑一条试卷记录
     * @param examPaper
     * @return
     */
    @Update("update exampaper set  examId=#{examId},examineeId=#{examineeId} where id = #{id}")
    int edit(ExamPaper examPaper);

    /**
     * 根据条件查询符合条件的结果
     * @param map
     * @return
     */
    @Select({
            "<script>",
            "select id,examId,examineeId,status,totalScore,score,createTime,startExamTime,endExamTime,useTime from exampaper",
            "<where>",
            "<if test='examineeId != null'>",
            "AND examineeId = #{examineeId}",
            "</if>",
            "<if test='examId != null'>",
            "AND examId = #{examId}",
            "</if>",
            "<if test='status != null'>",
            "AND status = #{status}",
            "</if>",
            "</where>",
            "limit #{start},#{size}",
            "</script>"
    })
    List<ExamPaper> findAllBySearch(Map<String, Object> map);

    /**
     * 根据id删除试卷记录
     * @param id
     * @return
     */
    @Delete("delete from exampaper where id = #{id}")
    int delete(Integer id);

    /**
     * 获取查询结果总数
     * @param map
     * @return
     */
    @Select({
            "<script>",
            "select count(id) from exampaper",
            "<where>",
            "<if test='examineeId != null'>",
            "AND examineeId = #{examineeId}",
            "</if>",
            "<if test='examId != null'>",
            "AND examId = #{examId}",
            "</if>",
            "<if test='status != null'>",
            "AND status = #{status}",
            "</if>",
            "</where>",
            "</script>"
    })
    int findCount(Map<String, Object> map);

    /**
     * 获取试卷
     * @param queryMap
     * @return
     */
    @Select({
            "<script>",
            "select * from exampaper",
            "<where>",
            "<if test='examineeId != null'>",
            "AND examineeId = #{examineeId}",
            "</if>",
            "<if test='examId != null'>",
            "AND examId = #{examId}",
            "</if>",
            "</where>",
            "</script>"
    })
    ExamPaper find(Map<String, Object> queryMap);

    /**
     * 跟新试卷开始时间
     * @param find
     * @return
     */
    @Update("update exampaper set startExamTime=#{startExamTime},status=#{status} WHERE id = #{id}")
    int updateStartExamTime(ExamPaper find);

    /**
     * 提交试卷
     * @param examPaper
     * @return
     */
    @Update("update exampaper set endExamTime=#{endExamTime},useTime=#{useTime},score=#{score},status=#{status} WHERE id = #{id}")
    int submitPaper(ExamPaper examPaper);

    /**
     * 获取考试信息
     * @param examId
     * @return
     */
    @Results({
            @Result(column = "name", property = "name"),
            @Result(column = "score", property = "score")
    }
    )
    @Select("SELECT a.score,b.`name` FROM exampaper a,examinee b WHERE a.examineeId = b.id AND examId = #{examId}")
    List<Map<String, Object>> getExamStats(Integer examId);

}
