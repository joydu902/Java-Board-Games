package fall18_207project.User;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import fall18_207project.GameCenter.GameCenterActivity;
import fall18_207project.GameCenter.R;
import fall18_207project.GameCenter.SaveLoad;

public class LogInActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    public UsersModel userFile = new UsersModel();
    public static final String filename = "usersFile.ser";
    public static String current_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        addLogInButtonListener();
        addSignUpButtonListener();
        loadUsersFromFile(filename);

        username =  findViewById(R.id.UserNameText);
        password =  findViewById(R.id.PassworsText);


    }

    /**
     * check whether username is valid and whether password is correct before login.
     * display messages when login unsuccessfully
     */

    private void addLogInButtonListener(){
        Button LogInButton = findViewById(R.id.LogInButton);
        LogInButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, GameCenterActivity.class);

            if ( check_valid(username.getText().toString(), password.getText().toString())  ) {
                current_user = username.getText().toString();
                startActivity(temp);
                makeToastLogInSuccessText();
            }
            else if (!check_valid_user_name(username.getText().toString())){
                Toast.makeText(this, "Invalid User Name", Toast.LENGTH_SHORT).show();
            }
            else if (!check_valid(username.getText().toString(), password.getText().toString())){
                Toast.makeText(this, "Password Incorrect", Toast.LENGTH_SHORT).show();
            }
            SaveLoad.name = current_user;



        });
    }

    /**
     * jump to SignUpActivity when press SignUp Botton in LoginActivity.
     */

    private void addSignUpButtonListener(){
        Button SignUpButton = findViewById(R.id.SignUpButton);
        SignUpButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, SignUpActivity.class);
            startActivity(temp);
        });
    }

    /**
     * read usersFile to check valid username.
     */

    public void loadUsersFromFile(String fileName){
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userFile = (UsersModel) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     *
     * @return true if username and password is correct.
     */
    public boolean check_valid(String username, String password)  {
        return userFile.getUsers().containsKey(username)
                && userFile.getUsers().get(username).equals(password);
    }

    /**
     *
     * @return true if username is in record.
     */

    public boolean check_valid_user_name(String username)  {
        return userFile.getUsers().containsKey(username);
    }


    private void makeToastLogInSuccessText() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }
}
