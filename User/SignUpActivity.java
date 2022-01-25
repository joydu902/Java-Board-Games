package fall18_207project.User;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.widget.Toast;

import fall18_207project.GameCenter.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText newName;
    private EditText newPassword;
    private SignUp signUp;
    private UsersModel userFile = new UsersModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        newName = findViewById(R.id.NewUserNameText);
        newPassword = findViewById(R.id.editText2);

        addNewSignUpButtonListener();
    }

    private void addNewSignUpButtonListener(){
        Button NewSignUpButton = findViewById(R.id.NewSignUpButton);
        NewSignUpButton.setOnClickListener((v) -> {
            Intent temp = new Intent(this, LogInActivity.class);
            this.signUp = new SignUp();
            loadUsersFromFile(SignUp.usersFile);
            signUp.setUsersModel(userFile);
            if ((!signUp.checkDuplicate(newName.getText().toString())) &&
                    (signUp.validPassword(newPassword.getText().toString()))){
                signUp.saveUser(newName.getText().toString(),newPassword.getText().toString());
                saveToFile(SignUp.usersFile);
                startActivity(temp);
                makeToastSignUpSuccessText();
            }
            else if (signUp.checkDuplicate(newName.getText().toString())){
                Toast.makeText(this, "User Name Already Exist", Toast.LENGTH_SHORT).show();
            }
            else if (!signUp.validPassword(newPassword.getText().toString())){
                Toast.makeText(this, "Password is too short!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveToFile(String filename){

        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(
                    this.openFileOutput(filename, MODE_PRIVATE));
            outputStream.writeObject(this.signUp.getUsersModel());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private void makeToastSignUpSuccessText() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }
}
