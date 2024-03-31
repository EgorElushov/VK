package vk.Responses;

public class UserResponseEntity {
    Long id;
    String passwordCheckStatus;

    public UserResponseEntity(Long id, String passwordCheckStatus) {
        this.id = id;
        this.passwordCheckStatus = passwordCheckStatus;
    }

    public Long getId() {
        return id;
    }

    public String getPasswordCheckStatus() {
        return passwordCheckStatus;
    }
}
