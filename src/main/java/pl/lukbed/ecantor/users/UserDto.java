package pl.lukbed.ecantor.users;

import lombok.Value;

@Value
public class UserDto {
    private final Long id;
    private final String mail;
    private final String username;

    static UserDto fromEntity(UserEntity entity) {
        return new UserDto(entity.getId(), entity.getMail(), entity.getUsername());
    }

    private UserDto(Long id, String mail, String username) {
        this.id = id;
        this.mail = mail;
        this.username = username;
    }
}
