package models;

public class User {
    private String username;
    private String passwordHash;

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Getters

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    // Setters

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return username + "," + passwordHash;
    }

    public static User fromString(String str) {
        String[] parts = str.split(",");
        return new User(parts[0], parts[1]);
    }
}
