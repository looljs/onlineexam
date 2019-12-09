package club.looli.onlineexam.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * 试卷实体
 */

@Data
public class ExamPaper {
    private Integer id;
    private Integer examId;//所属考试id
    private Integer examineeId;//考生id
    private Integer status;//试卷状态。 0 未考  ； 1 提交
    private Date createTime;
    private int totalScore;//总分
    private int score;//得分
    private Date startExamTime;//开始考试时间
    private Date endExamTime;//结束考试时间
    private int useTime;//考试用时
    private Exam exam;//考试实体
}
