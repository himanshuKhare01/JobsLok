package com.jobslok.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jobslok.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class PostTaskActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText task_location, task_title, task_description, task_date, task_budget, task_hour, task_priceperhour;
    String location, title, description, datePicker, budget, taskhour, taskpricehour;
    Switch task_switch;
    LinearLayout mainLayout, linearLayout1, linearLayout2;
    Spinner task_spinner, tasker_number;
    Button button;
    boolean isremote = false;
    private Calendar myCalendar;
    private int task_spinner_value = 0, tasker_counter = 1;
    CollectionReference collectionReferenceToPostTask;
    DocumentReference documentReferenceToUsers;
    String uid,type;
    private String name;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_task);

        toolbar = findViewById(R.id.toolbar_post);
        mainLayout = findViewById(R.id.postTaskLayout);
        task_location = mainLayout.findViewById(R.id.task_location);
        task_title = mainLayout.findViewById(R.id.task_title);
        task_description = mainLayout.findViewById(R.id.task_description);
        task_switch = mainLayout.findViewById(R.id.task_switch);
        task_date = findViewById(R.id.task_date_picker);
        task_budget = findViewById(R.id.task_budget);
        task_hour = findViewById(R.id.task_hour);
        task_priceperhour = findViewById(R.id.task_priceperhour);
        tasker_number = findViewById(R.id.tasker_number);
        linearLayout1 = findViewById(R.id.linear_layout);
        linearLayout2 = findViewById(R.id.linear_layout2);

        task_spinner = findViewById(R.id.task_spinner);
        button = findViewById(R.id.button_post);
        type=getIntent().getStringExtra("type");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        collectionReferenceToPostTask = FirebaseFirestore.getInstance().collection(type);
        documentReferenceToUsers=FirebaseFirestore.getInstance().collection("users").document(uid);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        task_switch.setOnCheckedChangeListener((compoundButton, b) -> {
            isremote = b;
            if (isremote)
                task_location.setVisibility(View.GONE);
            else
                task_location.setVisibility(View.VISIBLE);
        });
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        if (task_date != null)
            task_date.setOnClickListener(view -> {
                new DatePickerDialog(PostTaskActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            });
        if (task_spinner != null)
            task_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    task_spinner_value = i;
                    if (i == 0) {
                        linearLayout2.setVisibility(View.GONE);
                        linearLayout1.setVisibility(View.VISIBLE);
                    } else if (i == 1) {

                        linearLayout1.setVisibility(View.GONE);
                        linearLayout2.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        tasker_number.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tasker_counter = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        button.setOnClickListener(view -> {
           if (checkValidity()) {
                ProgressDialog progressDialog = new ProgressDialog(PostTaskActivity.this);
                progressDialog.setMessage("Please wait..");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.create();
                progressDialog.show();
                title = task_title.getText().toString();
                description = task_description.getText().toString();
                if (!isremote)
                    location = task_location.getText().toString();
                datePicker = task_date.getText().toString();
                if (task_spinner_value == 0)
                    budget = task_budget.getText().toString();
                else {
                    taskhour = task_hour.getText().toString();
                    taskpricehour = task_priceperhour.getText().toString();
                }

                HashMap<String, Object> map = new HashMap<>();
                map.put("title", title);
                map.put("description", description);
                map.put("remotely", isremote);
                map.put("uid",uid);
                map.put("type",type);
                map.put("status","open");
                map.put("offers","0");
                documentReferenceToUsers.get().addOnCompleteListener(task -> {
                    map.put("name",task.getResult().get("name"));
                    map.put("image",task.getResult().get("image"));
                });
               String myFormat = "EEE,dd MMM"; //In which you need put here
               SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
               map.put("postDate",sdf.format(new Date()));

               if (!isremote)
                    map.put("location", location);
                map.put("date", datePicker);
                if (task_spinner_value == 0)
                    map.put("budget", budget);
                else {
                    map.put("hour", taskhour);
                    map.put("pricehour", taskpricehour);
                }
                map.put("numberof_tasker", tasker_counter);
                collectionReferenceToPostTask.add(map).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Task Upload", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Something is not right " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(task -> {
                            progressDialog.dismiss();
                            Toast.makeText(this, "Something went wrong " + task.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                );
            }
        });
    }

    private boolean checkValidity() {
        if (task_title.getText().toString().isEmpty()) {
            task_title.setError("Please fill title");
            return false;
        } else if (task_description.getText().toString().isEmpty()) {
            task_description.setError("Please fill description");
            return false;
        } else if (!isremote &&task_location.getText().toString().isEmpty()) {
            task_location.setError("Please fill location");
            return false;
        } else if (task_date.getText().toString().isEmpty()) {
            task_date.setError("Please select a date");
            return false;
        } else if (task_spinner_value == 0 && task_budget.getText().toString().isEmpty()) {
            task_budget.setError("Please select a budget");
            return false;
        } else if (task_spinner_value == 1 && task_hour.getText().toString().isEmpty()) {
            task_hour.setError("Please select a hour");
            return false;
        } else if (task_spinner_value == 1 && task_priceperhour.getText().toString().isEmpty()) {
            task_priceperhour.setError("Please select a price");
            return false;
        } else
            return true;
    }

    private void updateLabel() {
        String myFormat = "EEE,dd MMM"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        task_date.setText(sdf.format(myCalendar.getTime()));
        task_date.setError(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            task_location.setError(null);
            assert data != null;
            try {
                String returnValue = data.getStringExtra("place");
                task_location.setText(returnValue);
            } catch (NullPointerException ex) {
                Toast.makeText(this, "Please select a location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void selectLocation(View view) {
        startActivityForResult(new Intent(PostTaskActivity.this, SearchLocationActivity.class), 0);
    }
}