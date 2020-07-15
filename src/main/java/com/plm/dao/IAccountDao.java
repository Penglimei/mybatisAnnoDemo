package com.plm.dao;

import com.plm.domain.Account;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface IAccountDao {

    /**
     *  查询所有账户信息，以及对应的用户信息
     *
     *  @Results 封装数据，还可以实现多对一等多表查询，以及是立即加载还是懒加载的设定
     *
     *      @Result(column = "uid",property = "user",
     *                     one = @One(select = "com.plm.dao.IUserDao.findUserById",
     *                     fetchType = FetchType.EAGER))
     *
     *            column = "uid" 是指在 account表中与 user表相关联的外键字段 uid
     *            property = "user" 是指在 Account实体类中定义的 User实体类属性
     *            @One 标签表示一对一关联查询(Mybatis中 将多对一视为 一对一)
     *            fetchType = FetchType.EAGER 属性表示立即加载
     * @return
     */
    @Select("select * from account")
    @Results(id="accountMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "uid",property = "uid"),
            @Result(column = "money",property = "money"),
            @Result(column = "uid",property = "user",
                    one = @One(select = "com.plm.dao.IUserDao.findUserById",
                    fetchType = FetchType.EAGER))
    })
    List<Account> findAll();


    /**
     *  根据用户id uid 找到用户所拥有的账户信息
     * @param userId
     * @return
     */
    @Select("select * from account where uid=#{userId}")
    List<Account> findAccountByUid(Integer userId);
}
