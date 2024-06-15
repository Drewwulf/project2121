package services;

import models.User;
import utils.SecurityUtils;

import java.io.*;

public class UserMananger {
    private static final String FILE_NAME = "data/users.txt";

    public static void addUser(User user) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
        writer.write(user.toString());
        writer.newLine();
        writer.close();
    }

    public static User authenticate(String username, String password) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        String line;
        while ((line = reader.readLine()) != null) {
            User user = User.fromString(line);
            if (user.getUsername().equals(username) && SecurityUtils.verifyPassword(password, user.getPasswordHash())) {
                reader.close();
                return user;
            }
        }
        reader.close();
        return null;
    }

    public static boolean userExists(String username) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        String line;
        while ((line = reader.readLine()) != null) {
            User user = User.fromString(line);
            if (user.getUsername().equals(username)) {
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;
    }
}
