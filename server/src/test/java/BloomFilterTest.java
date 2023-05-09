import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.ServerApplication;
import com.learning.entity.User;
import com.learning.service.user.UserService;
import com.learning.utils.RedisCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


/**
 * @Author PYB
 * @Date 2023/4/21 18:05
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)

public class BloomFilterTest {
    @Resource
    private RedissonClient redissonClient;
    @Test
    public void test6() throws Exception {
        String s = redissonClient.getConfig().toString();
        System.out.println(s
        );
    }
    @Test
    public void test7() throws Exception {
        RBloomFilter<Integer> oneBloomFilter = redissonClient.getBloomFilter("OneBloomFilter");
        oneBloomFilter.tryInit(10000L, 0.01);
        for (int i = 0; i < 10000; i++) {
            oneBloomFilter.add(i);
        }
        boolean contains = oneBloomFilter.contains(5854);
        System.out.println();
    }
}
