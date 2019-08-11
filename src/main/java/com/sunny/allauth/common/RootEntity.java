package com.sunny.allauth.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 实体根
 *
 * @author JasonLi
 * @date 2019/3/1 17:00
 * @since 1.0
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
public class RootEntity {
    @Id
    @Column
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Column
    @Version
    private Long version;
}
