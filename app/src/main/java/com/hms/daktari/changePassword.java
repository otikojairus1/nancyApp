package com.hms.daktari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class changePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
    }

    public void saveNewPassword(View view) {

        EditText oldpwd = (EditText)findViewById(R.id.editTexsuihistEmail);
        EditText newpassword = (EditText)findViewById(R.id.editTextPaishdsihssword);

        String oldpwdstring = oldpwd.getText().toString();
        String newpwd = newpassword.getText().toString();

        if (TextUtils.isEmpty(oldpwdstring)) {
            Toast.makeText(getApplicationContext(), "please enter your current password before you proceed",
                    Toast.LENGTH_SHORT).show(); // or break, continue, throw
        }else if(TextUtils.isEmpty(newpwd)){
            Toast.makeText(getApplicationContext(), "please enter your new password before you proceed",
                    Toast.LENGTH_SHORT).show();
        }else{
            DBHelper DB = new DBHelper(getApplicationContext());
            if (DB.ispasswordValid(oldpwdstring)) {
                //error message if there is no match
                Toast.makeText(getApplicationContext(), "invalid old password", Toast.LENGTH_SHORT).show();
            }else{
                String[] array = new String[]{oldpwdstring};
                ContentValues contentValues = new ContentValues();
                //contentValues.put("id",);
                contentValues.put("password",newpwd);

                if(DB.changepwd(contentValues,array)){
                    Toast.makeText(getApplicationContext(), " working", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), " password update successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Dashboard.class));
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
                finish();
            }
        }
    }
}