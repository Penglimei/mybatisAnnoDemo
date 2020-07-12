package com.plm.dao;

import com.plm.domain.User;

import java.util.List;

/**
 *  用户持久层接口
 *
 *
 *  在 mybatis 中把持久层的操作接口名称叫做 Mapper
 *  在此只是为了与 Spring 保持一致
 *  因此 IUserDao.java 等价于 IUserMapper.java
 *
 */
public interface IUserDao {

    /**
     *  查询所有
     */
    List<User> findAll();
}
