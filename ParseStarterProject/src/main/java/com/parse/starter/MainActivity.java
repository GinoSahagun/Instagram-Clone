/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


  Boolean signUpModeActive = true;
  TextView changeSignUpTextView = (TextView) findViewById(R.id.ChangeSignUpTextView);
  public void onClick(View view)
  {
    if (view.getId() == R.id.ChangeSignUpTextView)
    {
      Button signUp = (Button) findViewById(R.id.SignUpButton);
      if (signUpModeActive)
      {
        signUp.setText("Login In");
        signUpModeActive = false;
        changeSignUpTextView.setText("Or, Sign Up");
      }
      else
      {
        signUp.setText("Sign Up");
        signUpModeActive = true;
        changeSignUpTextView.setText("Or, Login");
      }
    }
  }



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    changeSignUpTextView.setOnClickListener(this);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  public void ToastMesage(String message)
  {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  public void SignUp(View view) {

    EditText emailText = (EditText) findViewById(R.id.email);
    EditText passwordField= (EditText) findViewById(R.id.password);

    if (emailText.getText().toString().matches("")
            || passwordField.getText().toString().matches("") )
      ToastMesage("A User Name and Password are Required");
    else {
      if (signUpModeActive) {
        ParseUser user = new ParseUser();
        user.setUsername(emailText.getText().toString());
        user.setPassword(passwordField.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if (e == null) {
              Log.i("Sign Up", "Registration Successful");
            } else {
              ToastMesage(e.getMessage());
            }
          }
        });
      }
      else
      {
        ParseUser.logInInBackground(emailText.getText().toString(), passwordField.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            if (user != null)
            {
              Log.i("Login", "Successful");
            }
            else
              ToastMesage(e.getMessage());
          }
        });
      }

    }

  }
}