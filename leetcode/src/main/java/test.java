import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.Base64;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @Author PYB
 * @Date 2023/6/16 17:25
 * @Version 1.0
 */
public class test {
    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String SECRET_KEY = "MySecretKey12345"; // 密钥
    public static String decode(String encodedText) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ENCRYPTION_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(encodedText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        Queue<String> strings = new ArrayDeque<>();
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    // @SneakyThrows
    // public static void main(String[] args) {
    //     System.out.println(decode("Jy8+m/nJJ9cVutbAF0Ebhb6BL0QKza9a96fE9r5rgcEHikif2/TOwYxmDyKN+QbaPJmcfNV0a8fZZVByPwnNa6boQ/PSmPpJiQZ8mHettMzoBiLy2tGDDg/cVYEDxGhJqmZ/NPCgUGyv4d0cpnjwKINQHVK3z1fVSNSgEmjT9++9Ib82f8CAxQ+Ui0O9b+HQ"));
    //     System.out.println(decode("6YCa5ZWG5a696KGj77yM6YeR56eR5b6L546J77yM6aKQ5L2/5rCU5oyH"));
    // }


    public class Main {
        public  void main(String[] args) throws InterruptedException, ExecutionException {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Integer> future = executor.submit(new MyCallable());

            // 等待子线程执行完毕并获取返回值
            int result = future.get();
            System.out.println("子线程返回值: " + result);

            executor.shutdown();
            System.out.println("结束");
        }
    }

    class MyCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            // 模拟耗时操作
            Thread.sleep(3000);
            return 123;
        }
    }

}
