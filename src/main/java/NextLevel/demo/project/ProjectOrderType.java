package NextLevel.demo.project;

public enum ProjectOrderType {
    RECOMMEND("count(r)"),
    COMPLETION(""),
    USER("count(f)"),
    CREATED("");

    public String type;

    private ProjectOrderType(String type) {
        this.type = type;
    }
}
