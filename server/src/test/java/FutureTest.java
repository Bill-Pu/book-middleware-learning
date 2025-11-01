import io.reactivex.rxjava3.internal.util.LinkedArrayList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.*;

public class FutureTest {
    @Test
    public void test() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()-> System.out.println("test1"));
        executorService.submit(()-> System.out.println("test2"));
        executorService.submit(() -> 1);
        FutureTask<Integer> integerFutureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(2);
                Thread.sleep(5000);
                System.out.println(2.1);
                return 2;
            }
        });
        executorService.submit(integerFutureTask);
        Integer i = integerFutureTask.get();
        System.out.println(i);
    }
    @Test
    public void test2(){
        Stack<Object> objects = new Stack<>();
        LinkedList<Object> objects1 = new LinkedList<>();
        ArrayList<Object> objects2 = new ArrayList<>();

    }
}
