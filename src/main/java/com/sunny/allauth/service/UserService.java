package com.sunny.allauth.service;

import com.sunny.allauth.entity.UserEntity;
import com.sunny.allauth.enums.ActiveEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

/**
 * UserService
 *
 * @author lijunsong
 * @date 2019/8/28 13:32
 * @since 1.0
 */
public interface UserService {

    UserEntity save(UserEntity userentity);

    UserEntity find(String id);

    List<UserEntity> findAll();

    List<UserEntity> findAll(List<String> ids);

    List<UserEntity> findAll(Sort sort);

    Page<UserEntity> findAll(Pageable pageable);

    void delete(String id);

    void delete(List<UserEntity> userentity);

    void delete(UserEntity userentity);

    void deleteAll();

    long count();

    UserEntity findByUserName(String userName);

    Set<String> findRoles(String userName);

    Set<String> findPermissions(String userName);

    void updateStatus(String userName, ActiveEnum status);
}