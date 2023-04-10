package bjr.payamigo;

public enum Role {
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    USER("USER");

    private String userRole;

    Role(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }
}
