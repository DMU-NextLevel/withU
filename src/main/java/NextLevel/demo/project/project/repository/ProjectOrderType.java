package NextLevel.demo.project.project.repository;

import java.util.Arrays;

public enum ProjectOrderType {
    RECOMMEND(" like_count "),
    COMPLETION(" completion_rate "),
    USER(" user_count "),
    CREATED(" fp.created_at ") ,
    EXPIRED(" fp.expired ");
    ;

    public String type;

    ProjectOrderType(String type) {
        this.type = type;
    }

    public static ProjectOrderType getType(String type) {
        return Arrays.stream(ProjectOrderType.values()).filter(t -> t.name().equals(type)).findFirst().orElse(ProjectOrderType.RECOMMEND);
    }
}
