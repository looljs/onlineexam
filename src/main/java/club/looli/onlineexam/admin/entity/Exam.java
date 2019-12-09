package club.looli.onlineexam.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * 考试实体
 */

@Data
public class Exam {
    public Exam(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Exam() {
        super();
    }

    private Integer id;//id
    private String name;//考试名称
    private Integer subjectId;//所属学科id
    private Date startTime;//考试开始时间
    private Date endTime;//考试结束时间
    private Integer totalScore;//总分
    private Integer questionNum;//试题总数
    private Integer passScore;//及格分数
    private Integer singleQuestionNum;//单选题数量
    private Integer muiltQuestionNum;//多选题数量
    private Integer chargeQuestionNum;//判断题数量
    private Integer avaliableTime;//考试所需时间
    private Integer paperNum;//试卷数量
    private Integer examedNum;//已考人数
    private Integer passNum;//及格人数
    private Date createTime;//添加时间
}
