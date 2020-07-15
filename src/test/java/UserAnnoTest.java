import com.plm.dao.IUserDao;
import com.plm.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class UserAnnoTest {

    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private IUserDao userDao;

    @Before
    public void init() throws Exception {

        // 1、读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 2、创建 SqlSessionFactory工厂
        factory = new SqlSessionFactoryBuilder().build(in);
        // 3、使用工厂生产 SqlSession对象
       session = factory.openSession();
        // 4、使用 SqlSession创建 Dao接口的代理对象
        userDao = session.getMapper(IUserDao.class);

    }

    @After
    public void destory() throws Exception {
        // 手动提交
        session.commit();
        // 6、 释放资源
        session.close();
        in.close();
    }

    /**
     *  测试 注解版查询所有用户信息
     */
    @Test
    public void testFindAll()  {

        // 5、使用代理对象执行方法
        List<User> users = userDao.findAll();
        for(User user : users){
            System.out.println("========每个用户的信息========");
            System.out.println(user);
            System.out.println(user.getAccounts());
        }
    }

    /**
     *  测试 根据用户id 查询用户信息
     */
    @Test
    public void testFindUserById(){

        User user = userDao.findUserById(55);
        System.out.println(user);
    }

    /**
     *  测试 注解版根据用户名模糊查询所有用户信息
     */
    @Test
    public void testFindUserByName()  {

        // 5、使用代理对象执行方法
        List<User> users = userDao.findUserByName("%王%");
        for(User user : users){
            System.out.println(user);
        }
    }

    /**
     *  测试 注解版保存用户信息
     */
    @Test
    public void testSaveUser(){
        // 5、使用代理对象执行方法
        User user = new User();
        user.setUserName("anno insert test");
        user.setUserAddress("cccc");
        user.setUserSex("女");
        user.setUserBirthday(new Date());
        userDao.saveUser(user);

        System.out.println(user);
    }

    /**
     *  测试 更新用户信息
     */
    @Test
    public void testUpdateUser(){

        User user = new User();
        user.setUserId(56);
        user.setUserName("anno update test");
        user.setUserAddress("cccc");
        user.setUserSex("女");
        user.setUserBirthday(new Date());

        userDao.updateUser(user);
        System.out.println(user);
        System.out.println(user.getUserId());
    }

    /**
     *  测试 根据用户id 删除用户信息
     */
    @Test
    public void testDeleteUserById(){

        userDao.deleteUserById(56);
    }
}
