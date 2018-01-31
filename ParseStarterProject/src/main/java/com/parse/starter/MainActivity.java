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
import android.widget.EditText;
import android.widget.Switch;
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

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


  public void Onclick(View view)
  {
    if (view.getId() == R.id.ChangeSignUpTextView)
  }



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);





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
    else
    {
      ParseUser user = new ParseUser();
      user.setUsername(emailText.getText().toString());
      user.setPassword(passwordField.getText().toString());
      user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null)
          {
            Log.i("Sign Up", "Registration Successful");
          }
          else
          {
            ToastMesage(e.getMessage());
          }
        }
      });
    }


  }
}