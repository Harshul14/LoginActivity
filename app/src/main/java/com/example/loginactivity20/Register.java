package com.example.loginactivity20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    TextView signin;
    EditText fullname, phone, emailid, password, confirmpassword;
    Button registerbutton;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signin = findViewById(R.id.signinbuton);

        fullname = findViewById(R.id.fullname);
        phone = findViewById(R.id.phone);
        emailid = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);

        registerbutton = findViewById(R.id.registerbutton);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {
        String inputFullName = fullname.getText().toString();
        String inputPhone = phone.getText().toString();
        String inputEmailId = emailid.getText().toString();
        String inputPassword = password.getText().toString();
        String inputConfirmPassword = confirmpassword.getText().toString();

        if (emailid.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Email Address", Toast.LENGTH_SHORT).show();
        } else {
            if (!inputEmailId.matches(emailPattern)) {
                emailid.setError("Invalid Email!");
            } else if (inputPassword.isEmpty() || inputPassword.length() < 6) {
                password.setError("Enter Password again");
            } else if (!inputPassword.equals(inputConfirmPassword)) {
                confirmpassword.setError("Password Not Matched");
            } else {
                progressDialog.setMessage("Please Wait");
                progressDialog.setTitle("Registration");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();


                //inputFullName,inputPhone abhi tk liye nhi hai khi bhi
                mAuth.createUserWithEmailAndPassword(inputEmailId, inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                            sendUserToNextActivity();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(Register.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}