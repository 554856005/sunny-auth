package com.sunny.allauth.service.impl;

import com.sunny.allauth.entity.UserEntity;
import com.sunny.allauth.enums.ActiveEnum;
import com.sunny.allauth.repository.UserRepo;
import com.sunny.allauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

/**
 * UserServiceImpl
 *
 * @author lijunsong
 * @date 2019/8/28 13:32
 * @since 1.0
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserEntity save(UserEntity userentity) {
        return userRepo.save(userentity);
    }

    @Override
    public UserEntity find(String id) {
        return userRepo.getOne(id);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepo.findAll();
    }

    @Override
    public List<UserEntity> findAll(List<String> ids) {
        return userRepo.findAllById(ids);
    }

    @Override
    public List<UserEntity> findAll(Sort sort) {
        return userRepo.findAll(sort);
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public void delete(String id) {
        userRepo.deleteById(id);
    }

    @Override
    public void delete(List<UserEntity> userentity) {
        userRepo.deleteInBatch(userentity);
    }

    @Override
    public void delete(UserEntity userentity) {
        userRepo.delete(userentity);
    }

    @Override
    public void deleteAll() {
        userRepo.deleteAll();
    }

    @Override
    public long count() {
        return userRepo.count();
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    @Override
    public Set<String> findRoles(String userName) {
        return null;
    }

    @Override
    public Set<String> findPermissions(String userName) {
        return null;
    }

    @Override
    public void updateStatus(String userName, ActiveEnum status) {
        UserEntity userEntity = userRepo.findByUserName(userName);
        if (null == userEntity) {
            log.warn("can't find user by userName [{}]", userName);
            return;
        }
        userEntity.setStatus(status);
    }

}