package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Gender gender;
    private UserStatus status;
    private String picture;
    private String timestamp;

    public User(String name, String surname, String email, String password, Gender gender, UserStatus status, String picture) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.status = status;
        this.picture = picture;
    }


}
