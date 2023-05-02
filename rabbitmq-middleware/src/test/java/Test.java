import com.learning.RabbitApplication;
import com.learning.event.LoginEventPublisher;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Author PYB
 * @Date 2023/5/2 18:43
 * @Version 1.0
 */
@SpringBootTest(classes = RabbitApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class Test {
    @Resource
    private LoginEventPublisher publisher;
    @org.junit.Test
    public void test1() throws Exception {
        publisher.publish();
    }
}
