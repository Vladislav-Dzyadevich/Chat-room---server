import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private static final UserList instance = new UserList();

    private Gson gson;
    private final List<User> allUsers = new ArrayList<>();

    private UserList() {
        gson = new GsonBuilder().create();
    }

    public static UserList getInstance() {
        return instance;
    }


    public synchronized void add(User user) {
        allUsers.add(user);
    }

    public synchronized void remove(User user) {
        allUsers.remove(user);
    }


    public synchronized String toJSON(int n) {
        if (n >= allUsers.size()) {
            return null;
        }
        return gson.toJson(allUsers);
    }

    public static User fromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, User.class);
    }

    public boolean isUserPresentInDatabase(String login, String password) {
        for (User registerUser : allUsers) {
            if (login.equals(registerUser.getLogin()) && password.equals(registerUser.getPassword())) {
                registerUser.setStatus(UserStatus.ONLINE);
                return true;
            }
        }
        return false;
    }

    public void logout(User user) {
        user.setStatus(UserStatus.OFFLINE);
    }

    public void login(User user) {
        user.setStatus(UserStatus.ONLINE);
    }

    @Override
    public String toString() {
        return "Пользователи чата" + allUsers +
                ".";
    }
}
