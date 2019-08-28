package com.sunny.allauth.repository;

import com.sunny.allauth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * UserRepo
 *
 * @author lijunsong
 * @date 2019/8/28 13:32
 * @since 1.0
 */
public interface UserRepo extends JpaRepository<UserEntity, String> {
    UserEntity findByUserName(String userName);
}