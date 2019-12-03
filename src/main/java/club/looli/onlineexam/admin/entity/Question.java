package club.looli.onlineexam.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * 试题实体类
 */
@Data
public class Question {
    private Integer id;
    private Integer subjectId;//学科专业类型
    private int questionType;//试题类型
    private String title;//题目
    private int score;//分值
    private String attrA;//选项A
    private String attrB;//选项B
    private String attrC;//选项C
    private String attrD;//选项D
    private String answer;//正确答案
    private Date createTime;//添加时间
}
