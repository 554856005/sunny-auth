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
 * UserEntity
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
@Table(name = "tt_user")
public class UserEntity extends BaseEntity {
    /**
     * 姓名
     */
    @Column(columnDefinition = "varchar(20) comment '姓名'")
    private String userName;

    /**
     * 工号
     */
    @Column(columnDefinition = "varchar(20) comment '工号'")
    private String code;

    /**
     * 登录名
     */
    @Column(columnDefinition = "varchar(30) comment '登录名'", unique = true, nullable = false)
    private String loginName;

    /**
     * 登录密码
     */
    @Column(columnDefinition = "varchar(255) comment '登录密码'")
    private String password;

    /**
     * 加密盐
     */
    @Column(columnDefinition = "varchar(255) comment '加密盐'")
    private String salt;

    /**
     * 状态
     */
    @Column(columnDefinition = "varchar(10) comment '状态'")
    private ActiveEnum status;
}
