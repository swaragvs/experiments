package service;


import util.FileUtil;
import java.util.HashMap;
import model.User;
import model.Restaurant;
import model.Order;
import model.DeliveryPerson;
import model.MenuItem;

public class UserService {
    private HashMap<String, User> users;

    public UserService() {
        users = FileUtil.loadUsers();
        if (users == null) {
            users = new HashMap<>();
        }
    }

    // Authenticate using userId (as used by the GUI)
    public boolean authenticate(String userId, String password) {
        User user = users.get(userId);
        return user != null && user.getPassword() != null && user.getPassword().equals(password);
    }

    // Backward compatible method name expected by GUI
    public boolean authenticateUser(String userId, String password) {
        return authenticate(userId, password);
    }

    public void registerUser(User user) {
        users.put(user.getUserId(), user);
        FileUtil.saveUsers(users);
    }

    // Convenience method used by GUI
    public void registerUser(String userId, String name, String email, String password, String phone) {
        User user = new User(userId, name, email, password, phone);
        registerUser(user);
    }

    public User getUserById(String userId) {
        return users.get(userId);
    }

    // Other methods related to user management

    public HashMap<String, User> getUsers() {
        return users;
    }
}
