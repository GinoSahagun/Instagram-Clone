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
import android.hardware.input.InputManager;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {


  Boolean signUpModeActive = true;
  TextView changeSignUpTextView = (TextView) findViewById(R.id.ChangeSignUpTextView);
  EditText passwordField = (EditText) findViewById(R.id.password);

  //Pressing enter in the keyboard will allow the user to right away enter sign up or login
  //Param i is the key pressed and represented as a integer
  @Override
  public boolean onKey(View view, int i, KeyEvent keyEvent)
  {

    if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)
    {
        SignUp(view);
    }

    return false;
  }


  //A Click Listener for Change Sign Up Text View
  //A Click Listener for the background to softly close the keyboard while opened
  @Override
  public void onClick(View view)
  {
    //Change Sign Up Text View when clicked will switch Sign up to Login
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
    //If the Background is Clicked and the Input is open it will close
    else if (view.getId() == R.id.logoImage || view.getId() == R.id.backgroundLogin)
    {
      InputMethodManager inputBoard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputBoard.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }

  }



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    passwordField = (EditText) findViewById(R.id.password);

    passwordField.setOnKeyListener(this);

    RelativeLayout background = (RelativeLayout) findViewById(R.id.backgroundLogin);
    ImageView logo = (ImageView) findViewById(R.id.logoImage);

    background.setOnClickListener(this);
    logo.setOnClickListener(this);



    changeSignUpTextView.setOnClickListener(this);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  //Recieve a String to create a Toast
  public void ToastMesage(String message)
  {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  //Param view onclick for Sign or Login
  //Depending on the Button Clicked the user is either signed up or logged in
  public void SignUp(View view) {

    EditText emailText = (EditText) findViewById(R.id.email);
    passwordField = (EditText) findViewById(R.id.password);

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