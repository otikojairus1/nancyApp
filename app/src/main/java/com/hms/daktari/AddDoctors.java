package com.hms.daktari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.widget.Spinner;

public class AddDoctors extends AppCompatActivity {
    String gsession, gender;
    TextView deletedoctor;
    String depart = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctors);

        String session = getIntent().getStringExtra("session");
        gsession = session;
        deletedoctor = (TextView)findViewById(R.id.deletedoctor);
        if (gsession != null){
            deletedoctor.setVisibility(View.VISIBLE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
    }

    public void onBack(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
        //intent.putExtra("username", user.getEmail());
        startActivity(intent);
    }

    public void addDoctor(View view) {


        EditText name = (EditText)findViewById(R.id.doctorname);
        EditText email = (EditText)findViewById(R.id.doctoremail);
        EditText mobile = (EditText)findViewById(R.id.doctorsnumbeer);
        EditText password = (EditText)findViewById(R.id.doctorpassword);
        //EditText department = (EditText)findViewById(R.id.department);

        String DoctorsName = name.getText().toString();
        String DoctorsEmail = email.getText().toString();
        String DoctorsMobile = mobile.getText().toString();
        String DoctorsPassword = password.getText().toString();
       // String DoctorsDepartment = department.getText().toString();

        if (TextUtils.isEmpty(DoctorsName)) {
            Toast.makeText(getApplicationContext(), "please enter the doctor's name before you proceed",
                    Toast.LENGTH_SHORT).show(); // or break, continue, throw
        }else if(TextUtils.isEmpty(DoctorsEmail)){
            Toast.makeText(getApplicationContext(), "please enter the doctor's email before you proceed",
                    Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(DoctorsMobile)){
            Toast.makeText(getApplicationContext(), "please enter the doctor's phone number before you proceed",
                    Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(DoctorsPassword)){
            Toast.makeText(getApplicationContext(), "please enter the doctor's login password before you proceed",
                    Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(DoctorsPassword)){
            Toast.makeText(getApplicationContext(), "please enter the doctor's department before you proceed",
                    Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(DoctorsPassword)){

            //check existing doctor

            DBHelper DB = new DBHelper(getApplicationContext());

            if(DB.isDoctorAccountExists(DoctorsEmail)){
                Toast.makeText(getApplicationContext(), "The specified doctor's email already exists in the system", Toast.LENGTH_SHORT).show();
            }else{

                ContentValues contentValues = new ContentValues();
                //contentValues.put("id",);
                contentValues.put("username",DoctorsName);
                contentValues.put("password",DoctorsPassword);
                contentValues.put("email",DoctorsEmail);
                contentValues.put("gender",gender);
                contentValues.put("phone",DoctorsMobile);
                contentValues.put("department",depart);

                //  DBHelper DB = new DBHelper(getApplicationContext());
                DB.insertDoctor(contentValues);
                Toast.makeText(getApplicationContext(), "Doctor registered successfully", Toast.LENGTH_SHORT).show();
                // Toast.makeText(getApplicationContext(),"working",Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(getApplicationContext(), "please enter the correct doctor's department before you proceed",
                    Toast.LENGTH_SHORT).show();


        }





    }

    private void addDepartmentDetails(String doctorsDepartment, String doctorsemail) {

    }

    public void deleteDoctor(View view) {
        Intent intent = new Intent(getApplicationContext(), DoctorsList.class);
        intent.putExtra("type", "admin");
        startActivity(intent);

    }

    public void onRadioButtonClicked2(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_piratsdes:
                if (checked)
                    // Pirates are the best
                    gender = "Male";
                Toast.makeText(getApplicationContext(), "you selected male as your gender", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio_sdninjas:
                if (checked)
                    // Ninjas rule
                    gender = "Female";
                Toast.makeText(getApplicationContext(), "you selected female as your gender", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirwatess:
                if (checked)
                    // Pirates are the best
                    depart = "Allergist";
                Toast.makeText(getApplicationContext(), "you selected Allergist as your department\"", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio_wninjass:
                if (checked)
                    // Ninjas rule
                    depart = "Dermatologist";
                Toast.makeText(getApplicationContext(), "you selected Dermatologist as your department\"", Toast.LENGTH_SHORT).show();
                break;

            case R.id.radio_dawninjass:
                if (checked)
                    // Ninjas rule
                    depart = "Cardiology";
                Toast.makeText(getApplicationContext(), "you selected Cardiology as your department\"", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio_wdaninjass:
                if (checked)
                    // Ninjas rule
                    depart = "Endocriology";
                Toast.makeText(getApplicationContext(), "you selected Endocriology as your department", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}