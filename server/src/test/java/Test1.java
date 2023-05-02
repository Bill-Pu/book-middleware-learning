import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.ServerApplication;
import com.learning.entity.User;
import com.learning.service.user.UserService;
import com.learning.utils.RedisCache;
import org.junit.Test;
import org.junit.runner.RunWith;
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

public class Test1 {
    @Resource
    private PasswordEncoder encodedKeySpec;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private UserService userService;
    @Resource
    private RedisCache redisCache;
    @Test
    public void test1(){
        redisTemplate.opsForValue().set("name","张三");
        redisTemplate.opsForValue().set("age","18");
        User user = new User();
        user.setId(1L);
        user.setUserName("张三");
        redisTemplate.opsForValue().set("user:demo:1",user);

        System.out.println(redisTemplate.opsForValue().get("name"));
    }
    @Test
    public void test2(){
        User byId = userService.getById(1);
        System.out.println(byId.toString());
    }
    @Test
    public void test3() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setId(1L);
        user.setUserName("张三");
        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        User user1 = objectMapper.readValue(s, User.class);
        System.out.println();
    }
    @Test
    public void test4(){
        String encode = encodedKeySpec.encode("123");
        System.out.println(encode);
    }
    @Test
    public void test5(){
//        redisTemplate.opsForValue().setIfAbsent()
        User user = new User();
        user.setUserName("John");
        user.setPassword("123");
        userService.save(user);

        User byId = userService.getById(user.getId());
        System.out.println();
    }

}
