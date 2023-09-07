import io.reactivex.rxjava3.core.Completable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author PYB
 * @Date 2023/5/15 19:05
 * @Version 1.0
 */
public class Test {
    @org.junit.Test
    public void test() {
            List<Book> books = new ArrayList<>();
            Collections.addAll(books,
                    new Book("笑傲江湖", "金庸", 99, 365),
                    new Book("笑傲江湖", "金庸", 99, 365),
                    new Book("神雕侠侣", "金庸", 99, 365),
                    new Book("雪中悍刀行", "烽火戏诸侯", 35, 320),
                    new Book("雪中悍刀行", "烽火戏诸侯", 35, 320),
                    new Book("剑来", "烽火戏诸侯", 35, 320),
                    new Book("西游记", "吴承恩", 56, 198),
                    new Book("三国演义", "罗贯中", 58, 230),
                    new Book("水浒传", "施耐庵", 55, 200),
                    new Book("红楼梦", "曹雪芹", 66, 280)
            );
        Book book = books.get(1);
        List<Book> collect = books.stream().sorted((p, a) -> {
            if (p.getAge() < a.getAge()) return 1;
            else return 0;
        }).collect(Collectors.toList());

//        List<Book> collect1 = books.stream().sorted(Comparator.comparing(Book::getAge).thenComparing()).collect(Collectors.toList());
    }

    //变更
}

