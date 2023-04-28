import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


@Data
@AllArgsConstructor
public class UserSimple implements Serializable {
    private static final long serialVersionUID = -671958543348052007L;

    private String userName;
    private String age;
}
