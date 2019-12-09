package club.looli.onlineexam.admin.entity;

import lombok.Data;

/**
 * 试卷答题详情实体
 */
@Data
public class ExamPaperAnswerDetails {
    private Integer id;
    private Integer examineeId;//考生id
    private Integer examId;//考试id
    private Integer examPaperId;//考卷id
    private Integer questionId;//试题id
    private String answer;//答案
    private Integer isCorrect;//是否正确
    private Question question;//试题
}
