package NextLevel.demo.common;

import java.util.Map;
import lombok.Getter;

@Getter
public class SuccessResponse {

    private String message;
    private Map<String, Object> data;

    public SuccessResponse(String message, Object data) {
        this.message = message;
        if(data != null)
            this.data = Map.of("data", data);
        else
            this.data = Map.of("data", "null");
    }

}
