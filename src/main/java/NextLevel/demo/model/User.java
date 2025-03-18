package NextLevel.demo.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String name;
    private int point;
    private String address;
    private String number;

    @Builder
    public User(Long userId, String name, int point, String address, String number) {
        this.id = userId;
        this.name = name;
        this.point = point;
        this.address = address;
        this.number = number;
    }
}
