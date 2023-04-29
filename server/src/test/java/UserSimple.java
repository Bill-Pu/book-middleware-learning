import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSimple implements Serializable {

    private static final long serialVersionUID = -8358390148492032099L;
    private String userName;
    private String age;
}
