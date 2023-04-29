import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.learning.ServerApplication;
import com.learning.utils.RedisUtil;
import jdk.nashorn.internal.ir.CallNode;
import net.minidev.json.JSONUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
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
    List<UserSimple> userSimples = new ArrayList<>();
    final String key = "redis:user:";

    @Before
    public void before() {
        userSimples.add(new UserSimple("1","1"));
        userSimples.add(new UserSimple("2","2"));
        userSimples.add(new UserSimple("3","3"));
        userSimples.add(new UserSimple("4","4"));    }
    @Test
    public void testList(){
        ListOperations<String, UserSimple> listOperations = redisTemplate.opsForList();
        for (UserSimple p : userSimples) {
            listOperations.leftPush(key + "list", p);
        }
        UserSimple userSimple = listOperations.rightPop(key);
        List<UserSimple> objects = (List<UserSimple>) redisUtil.rangeList(key);
        System.out.println(userSimple.toString());
    }
    @Test
    public void testSet(){
        SetOperations<String, UserSimple> stringUserSimpleSetOperations = redisTemplate.opsForSet();
        userSimples.forEach(userSimple -> stringUserSimpleSetOperations.add(key+"set",userSimple));
        UserSimple userSimple = stringUserSimpleSetOperations.pop(key+"set");
        System.out.println();
    }
    @Test
    public void testHash(){
        HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
        userSimples.forEach(userSimple -> stringObjectObjectHashOperations.put(key+"hash",userSimple.getUserName(),userSimple));
        UserSimple o = (UserSimple) stringObjectObjectHashOperations.get(key + "hash", userSimples.get(0).getUserName());
        System.out.println();
    }

}
