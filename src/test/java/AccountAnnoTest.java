import com.plm.dao.IAccountDao;
import com.plm.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class AccountAnnoTest {

    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private IAccountDao accountDao;

    @Before
    public void init() throws Exception {

        // 1、读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 2、创建 SqlSessionFactory工厂
        factory = new SqlSessionFactoryBuilder().build(in);
        // 3、使用工厂生产 SqlSession对象
        session = factory.openSession();
        // 4、使用 SqlSession创建 Dao接口的代理对象
        accountDao = session.getMapper(IAccountDao.class);

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
        List<Account> accounts = accountDao.findAll();
        for(Account account : accounts){
            System.out.println("========每个账户的信息========");
            System.out.println(account);
            System.out.println(account.getUser());
        }
    }
}
