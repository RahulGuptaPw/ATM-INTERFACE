import java.util.*;

class Bank {
    private List<User> users;

    public Bank() {
        users = new ArrayList<>();
        // Add some sample users to the bank
        users.add(new User("user1", 1234, 1000.0));
        users.add(new User("user2", 5678, 2000.0));
        // Add more users as needed
    }

    public User authenticateUser(String userId, int pin) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPin() == pin) {
                return user;
            }
        }
        return null;
    }

    public User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}