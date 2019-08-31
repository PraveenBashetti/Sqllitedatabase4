package com.example.sqllitedatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.net.VpnService;
import android.os.Build;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener{
EditText editRoll,editName,editMark;
Button btnAdd,btnDelete,btnModify,btnView,btnViewall,btnShowinfo;
Menu menu;
SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editRoll=findViewById(R.id.editRoll);
        editMark=findViewById(R.id.editMarks);
        editName=findViewById(R.id.editName);
        btnAdd=findViewById(R.id.bAdd);
        btnDelete=findViewById(R.id.bDelete);
        btnModify=findViewById(R.id.bModify);
        btnView=findViewById(R.id.btVIEW);
        btnViewall=findViewById(R.id.bViewAll);
        btnShowinfo=findViewById(R.id.bShow);
        btnAdd.setOnClickListener(this);
        btnShowinfo.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnViewall.setOnClickListener(this);
        db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Student(rollno VARCHAR,name VARCHAR,marks VARCHAR );");

    }

    @Override
    public void onClick(View v) {
switch (v.getId())
{
    case R.id.bAdd:
        if(editRoll.getText().toString().trim().length()==0||editName.getText().toString().trim().length()==0||editMark.getText().toString().trim().length()==0) {
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            showmsg("ERROR","Invalid input");
            return;
        }
        db.execSQL("INSERT INTO student VALUES('"+editRoll.getText()+"','"+editName.getText()+
                "','"+editMark.getText()+"');");
        showmsg("Success", "Record added");
        cleartext();
        /*else
        {
            Toast.makeText(this, "Please enter value", Toast.LENGTH_SHORT).show();
            //showMessage("Error","Please enter value");
        }*/
        break;
    case R.id.bDelete:
       if(editRoll.getText().toString().trim().length()==0)
    {
        showmsg("Error", "Please enter Rollno");
        return;
    }
        Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editRoll.getText()+"'", null);
       //cursor is to point on the current
        if(c.moveToFirst())
        {
            db.execSQL("DELETE FROM student WHERE rollno='"+editRoll.getText()+"'");
            showmsg("Success", "Record Deleted");
        }
        else
        {
            showmsg("Error", "Invalid Rollno");
        }
        cleartext();
        break;
    case R.id.bModify:
        if(editRoll.getText().toString().trim().length()==0||editName.getText().toString().trim().length()==0||editMark.getText().toString().trim().length()==0)
        {
            showmsg("Error", "Please enter Rollno");
            return;
        }
        Cursor b=db.rawQuery("SELECT * FROM student WHERE rollno='"+editRoll.getText()+"'", null);
        if(b.moveToFirst())
        {
            db.execSQL("UPDATE student SET name='"+editName.getText()+"',marks='"+editMark.getText()+
                    "' WHERE rollno='"+editRoll.getText()+"'");
            showmsg("Success", "Record Modified");
        }
        else
        {
            showmsg("Error", "Invalid Rollno");
        }
        cleartext();
        Toast.makeText(this, "Modified", Toast.LENGTH_SHORT).show();
        break;
    case R.id.btVIEW:
        if(editRoll.getText().toString().trim().length()==0)
        {
            showmsg("Error", "Please enter Rollno");
            return;
        }
        Cursor a=db.rawQuery("SELECT * FROM student WHERE rollno='"+editRoll.getText()+"'", null);
        if(a.moveToFirst())
        {
            editName.setText(a.getString(1));
            editMark.setText(a.getString(2));
        }
        else
        {
            showmsg("Error", "Invalid Rollno");
            cleartext();
        }
        Toast.makeText(this, "View", Toast.LENGTH_SHORT).show();
        break;
    case R.id.bViewAll:
        Cursor d=db.rawQuery("SELECT * FROM student", null);
        if(d.getCount()==0)
        {
            showmsg("Error", "No records found");
            return;
        }
        StringBuffer buffer=new StringBuffer();
        while(d.moveToNext())
        {
            buffer.append("Rollno: "+d.getString(0)+"\n");
            buffer.append("Name: "+d.getString(1)+"\n");
            buffer.append("Marks: "+d.getString(2)+"\n\n");
        }
        showmsg("Student Details", buffer.toString());
        Toast.makeText(this, "ViewAll", Toast.LENGTH_SHORT).show();
        break;
    case R.id.bShow:
        showmsg("Developed:","Praveen Bashetti");

        Toast.makeText(this, "Showing", Toast.LENGTH_SHORT).show();
        break;
}

    }
    private void showmsg(String title,String msg)
    {
         AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
         alertDialog.setCancelable(true);
         alertDialog.setTitle(title);
         alertDialog.setMessage(msg);
         alertDialog.setIcon(R.mipmap.ic_launcher_round);
         alertDialog.show();
    }
    private void cleartext()
    {
        editRoll.setText("");
        editMark.setText("");
        editName.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
        MenuInflater inflater=new MenuInflater()
        inflater.inflate(R.menu.menuu, menu);
        return true;
    }
}
