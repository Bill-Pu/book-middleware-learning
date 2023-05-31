import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
	// 书籍名称
    private String bookName;
	// 作者
    private String author;
	// 作者的年龄
    private Integer age;
	// 书的价格
    private Integer price;
}