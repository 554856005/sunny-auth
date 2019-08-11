package com.sunny.allauth.entity;

import com.sunny.allauth.common.RootEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * UserRole
 *
 * @Description
 * @Author JasonLi
 * @Date 2019-08-11 13:31
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@Table(name = "tr_user_role")
public class UserRole extends RootEntity {
    @Column(columnDefinition = "varchar(36) comment '用户Id'", nullable = false)
    private String userId;
    @Column(columnDefinition = "varchar(36) comment '角色Id'", nullable = false)
    private String roleId;
}
