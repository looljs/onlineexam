package club.looli.onlineexam.admin.dao;


import club.looli.onlineexam.admin.entity.ExamPaper;
import club.looli.onlineexam.admin.entity.ExamPaperAnswerDetails;
import club.looli.onlineexam.admin.entity.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 试卷答题详情管理持久化接口
 */
@Mapper
@Repository
public interface ExamPaperAnswerDetailsDAO {
    /**
     * 增加一条试卷答题详情记录
     * @param examPaperAnswerDetails
     * @return
     */
    @Insert("insert into examPaperAnswerDetails (examId,examineeId,examPaperId,questionId,isCorrect) values " +
            "(#{examId},#{examineeId},#{examPaperId},#{questionId},#{isCorrect})")
    int add(ExamPaperAnswerDetails examPaperAnswerDetails);

    /**
     * 编辑一条试卷答题详情记录
     * @param examPaperAnswerDetails
     * @return
     */
    @Update("update examPaperAnswerDetails set  examId=#{examId},examineeId=#{examineeId} where id = #{id}")
    int edit(ExamPaperAnswerDetails examPaperAnswerDetails);

    /**
     * 根据条件查询符合条件的结果
     * @param map
     * @return
     */
    @Select({
            "<script>",
            "select id,examId,examineeId,questionId,answer,isCorrect,examPaperId from examPaperAnswerDetails",
            "<where>",
            "<if test='examineeId != null'>",
            "AND examineeId = #{examineeId}",
            "</if>",
            "<if test='examId != null'>",
            "AND examId = #{examId}",
            "</if>",
            "<if test='examPaperId != null'>",
            "AND examPaperId = #{examPaperId}",
            "</if>",
            "<if test='questionId != null'>",
            "AND questionId = #{questionId}",
            "</if>",
            "</where>",
            "<if test='start != null'>",
            "limit #{start},#{size}",
            "</if>",
            "</script>"
    })
    List<ExamPaperAnswerDetails> findAllBySearch(Map<String, Object> map);

    /**
     * 根据id删除试卷答题详情记录
     * @param id
     * @return
     */
    @Delete("delete from examPaperAnswerDetails where id = #{id}")
    int delete(Integer id);

    /**
     * 获取查询结果总数
     * @param map
     * @return
     */
    @Select({
            "<script>",
            "select count(id) from examPaperAnswerDetails",
            "<where>",
            "<if test='examineeId != null'>",
            "AND examineeId = #{examineeId}",
            "</if>",
            "<if test='examId != null'>",
            "AND examId = #{examId}",
            "</if>",
            "<if test='questionId != null'>",
            "AND questionId = #{questionId}",
            "</if>",
            "</where>",
            "</script>"
    })
    int findCount(Map<String, Object> map);

    /**
     * 查看试题
     * @param map
     * @return
     */
    @Select({
            "<script>",
            "SELECT a.id,a.title,a.score,a.attrA,a.attrB,a.attrC,a.attrD,a.questionType,b.answer FROM question a,examPaperAnswerDetails b " +
                    "WHERE b.examId = #{examId} AND b.examineeId=#{examineeId} AND b.questionId = a.id",
            "<if test='questionId != null'>",
            "AND questionId = #{questionId}",
            "</if>",
            "</script>"
    })
    List<Question> findAllBySearch2(Map<String, Object> map);

    /**
     * 提交答案
     * @param examPaperAnswerDetails
     * @return
     */
    @Update("update examPaperAnswerDetails set answer=#{answer},isCorrect=#{isCorrect} \n" +
            "WHERE examId=#{examId} and examineeId=#{examineeId} and questionId=#{questionId}")
    int submitAnswer(ExamPaperAnswerDetails examPaperAnswerDetails);

    @Select("SELECT a.id,a.title,a.score,a.attrA,a.attrB,a.attrC,a.attrD,a.questionType FROM question a,examPaperAnswerDetails b" +
            " WHERE b.id = #{examPaperId} and b.isCorrect = 1 AND b.questionId = a.id")
    List<Question> findAllBySearch3(Map<String, Object> map);

    /**
     * 查看试题
     * @param map
     * @return
     */
    @Select({
            "<script>",
            "SELECT a.id,a.title,a.score,a.attrA,a.attrB,a.attrC,a.attrD,a.questionType,b.answer,a.answer as trueAnswer FROM question a,examPaperAnswerDetails b " +
                    "WHERE b.examId = #{examId} AND b.examineeId=#{examineeId} AND b.questionId = a.id",
            "<if test='questionId != null'>",
            "AND questionId = #{questionId}",
            "</if>",
            "</script>"
    })
    List<Map<String,Object>> findAllBySearch4(Map<String, Object> map);
}
