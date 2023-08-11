import com.learning.ServerApplication;
import com.learning.config.RedisConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

@SpringBootTest
@RunWith(SpringRunner.class)

public class DelayQueueTest {
    @Resource
    RedisConfig redisConfig;
    @Resource
    RedisTemplate redisTemplate;
    @Test
    public void test(){
        redisTemplate.opsForValue().set("delay", "1");
        String delay = (String) redisTemplate.opsForValue().get("delay");
        System.out.println(delay);
        DelayQueue<Delayed> delayeds = new DelayQueue<>();
    }
}
