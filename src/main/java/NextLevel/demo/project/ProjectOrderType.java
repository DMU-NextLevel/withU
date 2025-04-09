package NextLevel.demo.project;

import java.util.Arrays;

public enum ProjectOrderType {
    RECOMMEND(" recommend_count "),
    COMPLETION(" completion_rate "),
    USER(" user_count "),
    CREATED(" p.created_at ");

    public String type;

    ProjectOrderType(String type) {
        this.type = type;
    }

    public static ProjectOrderType getType(String type) {
        return Arrays.stream(ProjectOrderType.values()).filter(t -> t.name().equals(type)).findFirst().orElse(ProjectOrderType.RECOMMEND);
    }
}
