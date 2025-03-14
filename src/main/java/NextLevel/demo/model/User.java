package NextLevel.demo.model;

import NextLevel.demo.role.UserRole;
import lombok.Getter;

@Getter
public class User {
    private Long userId;
    private String name;
    private String email;
    private int point;
    private String address;
    private String number;
}
