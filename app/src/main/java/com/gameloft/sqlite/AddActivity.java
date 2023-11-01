package com.gameloft.sqlite;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtPhone, edtAddress;
    private RadioGroup radioGender;
    private RadioButton rbGender;
    private Button btnAdd, btnCancel;
    private MyDatabase db = new MyDatabase(this);
    private Bundle b;
    Contact contact = new Contact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setTitle("Add Contact");
        init();
        b = getIntent().getExtras();
        if (b!=null){
            contact.setId(b.getInt("ID"));
            contact = db.getContact(contact.getId());
            setData();
        }
    }

    private void init(){
        edtName = (EditText) findViewById(R.id.edtName);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        radioGender = (RadioGroup) findViewById(R.id.radioGender);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }
    private  void setData(){
        getSupportActionBar().setTitle("Update Contact");
        btnAdd.setText("UPDATE");
        edtName.setText(contact.getName());
        edtAddress.setText(contact.getAddress());
        edtPhone.setText(contact.getPhone());
        if (contact.getGender().equals("Male")) {
            radioGender.check(R.id.radioMale);
        } else radioGender.check(R.id.radioFemale);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnAdd:
                add();
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }

    private void add(){
        contact.setName(edtName.getText().toString());
        contact.setPhone(edtPhone.getText().toString());
        contact.setAddress(edtAddress.getText().toString());
        long day = System.currentTimeMillis();
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(day));
        contact.setDate(date);
        String h = new SimpleDateFormat("hh:mm:ss").format(new Date(day));
        contact.setTime(h);
        int idrad = radioGender.getCheckedRadioButtonId();
        rbGender = (RadioButton) findViewById(idrad);
        contact.setGender(rbGender.getText().toString());

        if (b!=null){
            db.updateContact(contact);


        } else {
            contact.setId(db.addContact(contact));

        }

        db.close();
        Intent i = new Intent();
        Bundle b = new Bundle();
        b.putSerializable("RETURN", contact);
        i.putExtras(b);
        setResult(RESULT_OK,i);


        finish();
    }

}
