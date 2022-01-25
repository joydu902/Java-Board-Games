package fall18_207project.User;


/**
 * Sighup Class.
 */

public class SignUp {


    /**
     * usersModel
     */
    private UsersModel usersModel = new UsersModel();

    public static final String usersFile = "usersFile.ser";

    /**
     * check whether the username is duplicate with an existing username.
     */
    public boolean checkDuplicate(String username){
        return usersModel.getUsers().containsKey(username);

    }


    /**
     *We put some conditions on a valid password.
     * a valid password must longer than 6 characters.
     */
    public boolean validPassword(String password) {
        return password.length() >= 6;
    }



    /**
     * Add username and password to UsersFile if username and password are valid.
     */
    public void saveUser(String username, String password) {
            User newUser = new User(username, password);
            usersModel.addUser(newUser);

    }

    /**
     *
     * @return usersModel
     */
    public UsersModel getUsersModel() {
        return usersModel;
    }

    /**
     * set another usersModel.
     * @param usersModel
     */

    public void setUsersModel(UsersModel usersModel){
        this.usersModel = usersModel;
    }



}
