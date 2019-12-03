package club.looli.onlineexam.admin.entity;

import lombok.Data;

/**
 * 学科实体
 */

@Data
public class Subject {
    private Integer id;//id
    private String name;//学科名称
    private String remark;//学科备注

    public Subject(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
