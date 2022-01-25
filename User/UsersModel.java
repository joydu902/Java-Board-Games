package fall18_207project.User;


import java.io.Serializable;
import java.util.HashMap;
/**
 * UsersModel class
 */

public class UsersModel implements Serializable {

    private HashMap<String, String> users = new HashMap<>();

    /**
     * add a new user into users.
     */

    public void addUser(User user){
        this.users.put(user.getUsername(), user.getPassword());
    }



    public HashMap getUsers(){
        return this.users;
    }

    public void setUsers(HashMap<String, String> users) {
        this.users = users;
    }
}
