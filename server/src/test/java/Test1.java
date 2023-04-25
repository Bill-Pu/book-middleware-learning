import com.learning.ServerApplication;
import com.learning.entity.User;
import com.learning.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


/**
 * @Author PYB
 * @Date 2023/4/21 18:05
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)

public class Test1 {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private UserService userService;
    @Test
    public void test1(){
        redisTemplate.opsForValue().set("name","张三");
        redisTemplate.opsForValue().set("age","18");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }
    @Test
    public void test2(){
        User byId = userService.getById(1);
        System.out.println(byId.toString());
    }

}
