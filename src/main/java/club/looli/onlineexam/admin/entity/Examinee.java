package club.looli.onlineexam.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * 考生实体
 */
@Data
public class Examinee {
    private Integer id;//考生id
    private String name;//用户名
    private String password;//密码
    private Integer subjectId;//学科id
    private String trueName;//真实姓名
    private String tel;//手机号
    private Date createTime;//注册时间
}
