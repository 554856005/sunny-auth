package com.sunny.allauth.controller;

import com.sunny.allauth.entity.UserEntity;
import com.sunny.allauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController
 *
 * @author lijunsong
 * @date 2019/8/28 13:32
 * @since 1.0
 */
@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userentityService;

    @PostMapping("/userentity")
    public UserEntity save(@RequestBody UserEntity userentity){
        return userentityService.save(userentity);
    }

    @GetMapping("/userentity/{id}")
    public UserEntity getById(@PathVariable(value = "id") Long id){
        return userentityService.find(id);
    }

    @GetMapping("/userentity")
    public List<UserEntity> getAll(){
        return userentityService.findAll();
    }

    @DeleteMapping("/userentity/{id}")
    public void deleteById(@PathVariable(value = "id") Long id){
        userentityService.delete(id);
    }

    @DeleteMapping("/userentity")
    public void deleteAll(){
        userentityService.deleteAll();
    }

    @GetMapping("/userentity/count")
    public long count(){
        return userentityService.count();
    }
}