
import com.caring.dao.model.User;
import com.caring.dao.service.SysUserService;
import com.caring.wxrs.security.TokenHandler;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author james
 */
@EnableAutoConfiguration
@WebAppConfiguration
@ComponentScan("com.caring")
@RunWith(SpringJUnit4ClassRunner.class)
public class WechatAPITest {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TokenHandler managementTokenHandler;

    @Test
    @Ignore
    public void test() {

    }

    @Test
    @Ignore
    
    public void testAdmin() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin@2017");
        user.setPassword(managementTokenHandler.crypt(user.getPassword()));
        user = sysUserService.saveUser(user);
        LOG.info("username: {}, password: {}", user.getUsername(), user.getPassword());
    }
}
