import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.learning.ServerApplication;
import com.learning.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)

public class RedisListSetZSetTest {
    @Resource
    private RedisTemplate<String, UserSimple> redisTemplate;
    @Resource
    private RedisUtil redisUtil;
    @Test
    public void testList(){
        List<UserSimple> userSimples = new ArrayList<>();
        userSimples.add(new UserSimple("1","1"));
        userSimples.add(new UserSimple("2","2"));
        userSimples.add(new UserSimple("3","3"));
        userSimples.add(new UserSimple("4","4"));
        final String key = "redis:user:list";
        ListOperations<String, UserSimple> listOperations = redisTemplate.opsForList();
        userSimples.forEach(p->{
            listOperations.leftPush(key, p);
        });
        UserSimple userSimple = listOperations.rightPop(key);
        List<UserSimple> objects = (List<UserSimple>) redisUtil.rangeList(key);
//        JSON.parseObject()
//        Object userSimple = objects.get(0);
//        String s = JSONObject.toJSONString(userSimple, JSONWriter.Feature.LargeObject);
//        Object o = JSON.parse(s);
//        UserSimple userSimple1 = JSON.parseObject(s, UserSimple.class);
//        System.out.println();
    }
}
