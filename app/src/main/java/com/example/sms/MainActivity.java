package com.example.sms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.Manifest.permission.SEND_SMS;

public class MainActivity extends Activity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    Button send_btn;
    EditText editText_mob;
    EditText editText_msg;
    String Phone;
    String Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText_mob = (EditText)findViewById(R.id.mobile_id);
        editText_msg = (EditText)findViewById(R.id.msg_id);
        send_btn = (Button)findViewById(R.id.btn_send_sms);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        Phone = editText_mob.getText().toString();
        Message = editText_msg.getText().toString();
        if(ContextCompat.checkSelfPermission(this, SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, SEND_SMS)){}
            else
                ActivityCompat.requestPermissions(this,new String[]{SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
        ActivityCompat.requestPermissions(this,new String[]{SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
        return;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_SEND_SMS:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(Phone,null,Message,null,null);
                    Toast.makeText(this,"SMS sent",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(this,"SMS failed, Please try again",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}