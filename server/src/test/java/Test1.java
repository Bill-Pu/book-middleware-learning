import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.learning.domain.Student;
import org.junit.Test;

import java.util.HashMap;
import java.util.Locale;


/**
 * @Author PYB
 * @Date 2023/4/21 18:05
 * @Version 1.0
 */
public class Test1 {
    @Test
    public void test(){
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
}
