package RentalService.manager;

import RentalService.domain.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    Map<String, User> userMap = new HashMap<>();

    public void addUser(String id) {
        if (userMap.containsKey(id))
            return;
        User user = new User(id, "driver 1");
        userMap.put(id, user);
    }
}
