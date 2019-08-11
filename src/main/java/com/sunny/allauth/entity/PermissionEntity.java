package com.sunny.allauth.entity;

import com.sunny.allauth.common.BaseEntity;
import com.sunny.allauth.enums.ActiveEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 数据库命名规则
 * tm = table model
 * tt = table transaction
 * tr = table relation
 * 参考：https://blog.csdn.net/Qsir/article/details/72628127
 *
 * @author JasonLi
 * @date 2019/3/1 16:59
 * @since 1.0
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@Table(name = "tm_permission")
public class PermissionEntity extends BaseEntity {
    @Column(columnDefinition = "varchar(120) comment '权限'")
    private String permission;

    @Column(columnDefinition = "varchar(1000) comment '描述'")
    private String description;

    /**
     * 状态
     */
    @Column(columnDefinition = "varchar(10) comment '状态'")
    private ActiveEnum status;
}
