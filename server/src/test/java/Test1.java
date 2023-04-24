import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.ServerApplication;
import com.learning.domain.Student;
import com.learning.entity.User;
import com.learning.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Locale;



/**
 * @Author PYB
 * @Date 2023/4/21 18:05
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)

public class Test1 {
    @Test
    public void test(){
        ObjectMapper objectMapper = new ObjectMapper().setLocale(Locale.CHINA);
//        objectMapper.writeValueAsString()
        JSONObject st = (JSONObject) JSON.toJSON(new Student("张三", 18));
        String s = st.toString();
        String st2 = JSON.toJSONString(new Student("张三", 18));
        Student st3 = new Student("张三", 18);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("name", "张三");
        stringStringHashMap.put("age", "18");
        String s1 = JSON.toJSONString(stringStringHashMap);
        System.out.println(s1);
        String s2 = JSON.toJSONString(s1);
        System.out.println(s2);
        System.out.println(JSONObject.toJSONString(s1));
        System.out.println(JSONObject.toJSONString(s1.toString()));
        System.out.println(JSONObject.toJSONString(st3));
        String s3 = JSONObject.toJSONString(s1);
        String student = JSON.parseObject(s3, String.class);
        Student student1 = JSON.parseObject(student, Student.class);
        System.out.println();
    }
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
