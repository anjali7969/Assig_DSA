package QuestionNo7;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class App {
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;
    private static final int BOX_WIDTH = 200;
    private static final int BOX_HEIGHT = 30;

    private static Map<String, User> users = new HashMap<>();
    private static User currentUser;

    private static JLabel totalPostsLabel;
    private static JLabel followersLabel;
    private static JLabel followingLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Instagram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (users.containsKey(username)) {
                    User user = users.get(username);
                    if (user.getPassword().equals(password)) {
                        currentUser = user;
                        totalPostsLabel = new JLabel("Total Posts: " + currentUser.getTotalPosts() + "   ");
                        followersLabel = new JLabel("Followers: " + currentUser.getFollowers() + "   ");
                        followingLabel = new JLabel("Following: " + currentUser.getFollowing() + "   ");
                        showProfilePage();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Incorrect password!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "User not found! Please register.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (users.containsKey(username)) {
                    JOptionPane.showMessageDialog(frame, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    User newUser = new User(username, password);
                    users.put(username, newUser);
                    JOptionPane.showMessageDialog(frame, "Registration successful!");
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void showProfilePage() {
        JFrame profileFrame = new JFrame("Profile");
        profileFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username: " + currentUser.getUsername());

        JPanel horizontalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        horizontalPanel.add(totalPostsLabel);
        horizontalPanel.add(followersLabel);
        horizontalPanel.add(followingLabel);

        JButton uploadImageButton = new JButton("Upload Image");
        JButton followButton = new JButton("Follow");

        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(profileFrame, "Image uploaded successfully: " + selectedFile.getName());
                    currentUser.incrementTotalPosts();
                    totalPostsLabel.setText("Total Posts: " + currentUser.getTotalPosts() + "   ");
                }
            }
        });

        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser != null) { // Check if currentUser is not null
                    String followUsername = JOptionPane.showInputDialog(profileFrame, "Enter the username to follow:");
                    if (followUsername != null && !followUsername.isEmpty()) {
                        if (users.containsKey(followUsername)) {
                            User followUser = users.get(followUsername);
                            currentUser.follow(followUser);
                            currentUser.incrementFollowing();
                            followUser.incrementFollowers();
                            followersLabel.setText("Followers: " + currentUser.getFollowers() + "   ");
                            followingLabel.setText("Following: " + currentUser.getFollowing() + "   ");
                            JOptionPane.showMessageDialog(profileFrame, "You are now following " + followUsername);
                        } else {
                            JOptionPane.showMessageDialog(profileFrame, "User '" + followUsername + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftButtonPanel.add(uploadImageButton);
        leftButtonPanel.add(followButton);
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(100, 40));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profileFrame.dispose();
                currentUser = null;
                main(new String[]{});
            }
        });

        buttonPanel.add(logoutButton, BorderLayout.EAST);

        panel.add(usernameLabel);
        panel.add(horizontalPanel);
        panel.add(buttonPanel);

        profileFrame.add(panel);
        profileFrame.setVisible(true);
    }
}

class User {
    private String username;
    private String password;
    private int totalPosts;
    private int followers;
    private int following;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.totalPosts = 0;
        this.followers = 0;
        this.following = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getTotalPosts() {
        return totalPosts;
    }

    public void incrementTotalPosts() {
        totalPosts++;
    }

    public int getFollowers() {
        return followers;
    }

    public void incrementFollowers() {
        followers++;
    }

    public int getFollowing() {
        return following;
    }

    public void incrementFollowing() {
        following++;
    }

    public void follow(User user) {
        System.out.println("Following user: " + user.getUsername());
    }
}


