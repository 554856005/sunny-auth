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
 * RoleEntity
 *
 * @Description
 * @Author JasonLi
 * @Date 2019-08-11 13:30
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@Table(name = "tt_permission")
public class RoleEntity extends BaseEntity {
    /**
     * 角色名称
     */
    @Column(columnDefinition = "varchar(20) comment '角色名称'", nullable = false)
    private String name;

    /**
     * 角色代码
     */
    @Column(columnDefinition = "varchar(36) comment '角色代码'", nullable = false)
    private String code;

    /**
     * 角色目录
     */
    @Column(columnDefinition = "varchar(36) comment '角色目录'")
    private String catalog;

    /**
     * 所在组织Id
     */
    @Column(columnDefinition = "varchar(36) comment '组织Id'", nullable = false)
    private String orgId;

    /**
     * 描述
     */
    @Column(columnDefinition = "varchar(1000) comment '描述'")
    private String description;

    /**
     * 状态
     */
    @Column(columnDefinition = "varchar(10) comment '状态'", nullable = false)
    private ActiveEnum status;
}
