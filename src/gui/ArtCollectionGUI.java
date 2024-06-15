package gui;

import models.ArtPiece;
import models.User;
import services.ArtCollectionManager;
import services.UserMananger;
import utils.SecurityUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ArtCollectionGUI {
    private JFrame frame;
    private JTextField titleField;
    private JTextField artistField;
    private JTextField yearField;
    private JTextArea descriptionArea;
    private JTextArea displayArea;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private User currentUser;

    public ArtCollectionGUI() {
        frame = new JFrame("Art Collection Manager");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel loginPanel = createLoginPanel();
        container.add(loginPanel, BorderLayout.NORTH);

        JPanel inputPanel = createInputPanel();
        container.add(inputPanel, BorderLayout.CENTER);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        container.add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        loginPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    User user = UserMananger.authenticate(username, password);
                    if (user != null) {
                        currentUser = user;
                        displayArea.append("User " + username + " logged in.\n");
                    } else {
                        displayArea.append("Login failed for user " + username + ".\n");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error logging in.");
                }
            }
        });
        loginPanel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    if (!UserMananger.userExists(username)) {
                        String salt = SecurityUtils.generateSalt();
                        String hashedPassword = SecurityUtils.hashPasswordWithSalt(password, salt);
                        User user = new User(username, hashedPassword + ":" + salt);
                        UserMananger.addUser(user);
                        displayArea.append("User " + username + " registered.\n");
                    } else {
                        displayArea.append("User " + username + " already exists.\n");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error registering user.");
                }
            }
        });
        loginPanel.add(registerButton);

        return loginPanel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Artist:"));
        artistField = new JTextField();
        inputPanel.add(artistField);
        inputPanel.add(new JLabel("Year:"));
        yearField = new JTextField();
        inputPanel.add(yearField);
        inputPanel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea();
        inputPanel.add(descriptionArea);

        JButton addButton = new JButton("Add Art Piece");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser != null) {
                    try {
                        String title = titleField.getText();
                        String artist = artistField.getText();
                        int year = Integer.parseInt(yearField.getText());
                        String description = descriptionArea.getText();
                        ArtPiece artPiece = new ArtPiece(title, artist, year, description);
                        ArtCollectionManager.addArtPiece(artPiece);
                        displayArea.append("Added: " + artPiece.toString() + "\n");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error saving art piece.");
                    }
                } else {
                    displayArea.append("Please log in to add art pieces.\n");
                }
            }
        });
        inputPanel.add(addButton);

        return inputPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ArtCollectionGUI();
            }
        });
    }
}
