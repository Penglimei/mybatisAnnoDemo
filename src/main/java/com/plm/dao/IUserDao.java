package com.plm.dao;

import com.plm.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 *  用户持久层接口
 *
 *  在 mybatis 中把持久层的操作接口名称叫做 Mapper
 *  在此只是为了与 Spring 保持一致
 *  因此 IUserDao.java 等价于 IUserMapper.java
 *
 *  在 MyBatis中针对CURD一共有四个注解
 *      @Select
 *      @Insert
 *      @Update
 *      @Delete
 *
 */

//开启二级缓存，默认是一级缓存
@CacheNamespace(blocking = true)
public interface IUserDao {

    /**
     *  查询所有
     *
     *  @Results 封装数据，也可以解决实体类属性与表字段不一致的问题
     *
     *  id = true 代表这个字段是主键
     */
    @Select("select * from user")
    @Results(id = "userMap",value = {
            @Result(id = true,column = "id",property = "userId"),
            @Result(column = "username",property = "userName"),
            @Result(column = "address",property = "userAddress"),
            @Result(column = "sex",property = "userSex"),
            @Result(column = "birthday",property = "userBirthday"),
            @Result(column = "id",property = "accounts",
                    many = @Many(select = "com.plm.dao.IAccountDao.findAccountByUid",
                            fetchType = FetchType.LAZY))

    })
    List<User> findAll();

    /**
     *  根据用户id 查询用户信息
     * @param id
     * @return
     */
    @Select("select * from user where id=#{id}")
    @ResultMap(value = "userMap")
    User findUserById(Integer id);

    /**
     *  根据 用户名字模糊查询，用户信息
     * @param username
     * @return
     */
    @Select("select * from user where username like #{username}")
    @ResultMap(value = "userMap")
    List<User> findUserByName(String username);

    /**
     *  保存用户
     * @param user
     */
    @Insert("insert into user(username,address,sex,birthday) " +
            "values (#{username},#{address},#{sex},#{birthday})")
    @ResultMap(value = "userMap")
    void saveUser(User user);

    /**
     *  更新用户信息
     * @param user
     */
    @Update("update user set username=#{username},address=#{address},sex=#{sex},birthday=#{birthday} where id=#{id}")
    @ResultMap(value = "userMap")
    void updateUser(User user);

    /**
     * 根据 id 删除用户信息
     * @param id
     */
    @Delete("delete from user where id=#{id}")
    @ResultMap(value = "userMap")
    void deleteUserById(Integer id);
}
