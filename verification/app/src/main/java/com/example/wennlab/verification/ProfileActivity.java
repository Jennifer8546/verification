package com.example.wennlab.verification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/*import com.example.vrml.happychildapp.R;
import com.example.vrml.happychildapp.menu_choose;*/
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private TextView tvUserEmail;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText etUserName, etUserposition;
    private Button buttonSave;
    private Button buttonVerified;
    private TextView profile_Info;
    //USER 職位
    private String UserType="學生";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        //回到登入介面
        if (firebaseAuth.getCurrentUser() == null) {

            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            ProfileActivity.this.finish();
        }

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //一般登出畫面
        tvUserEmail = (TextView) findViewById(R.id.tvUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonlogout);
        buttonVerified=(Button) findViewById(R.id.verButton);
        etUserName = (EditText) findViewById(R.id.etUserName);
        buttonSave = (Button) findViewById(R.id.buttonsave);

        // 未完成處 參考:https://github.com/givemepassxd999/firebase_auth_manager_user/blob/master/app/src/main/java/com/gg/givemepass/firebaseauthmanageruser/MainActivity.java


       //顯示使用者信箱
        tvUserEmail.setText("歡迎" + user.getEmail() + "加入!");
        //使用者信箱驗證
        profile_Info = (TextView) findViewById(R.id.profile_info);
        buttonVerified.setOnClickListener(new View.OnClickListener() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            @Override
            public void onClick(View v) {
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "驗證信已寄出", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ProfileActivity.this, "無法傳送驗證信" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                if (user != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("\n");
                    sb.append("is email verified:");
                    sb.append(user.isEmailVerified());
                    sb.append("\n");
                    profile_Info.setText(sb.toString());
                }
            }
        });

                buttonSave.setOnClickListener(new View.OnClickListener() {
                    Intent intent = new Intent();

                    @Override
                    public void onClick(View v) {
                        //  saveUserInformation();
                        //   intent.setClass(ProfileActivity.this,menu_choose.class);
                        //  startActivity(intent);
                    }
                });
                buttonLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firebaseAuth.signOut();

                        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                        ProfileActivity.this.finish();
                    }
                });

                //點選老師或學生
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        switch (i) {
                            case R.id.StuButton:
                                UserType = "學生";
                                break;
                            case R.id.TeaButton:
                                UserType = "老師";
                                break;
                        }
                    }
                });

    /*資料儲存
    private void saveUserInformation() {
        String name = etUserName.getText().toString().trim();
        String position =UserType;
        if(name.equals("") || position.equals("")) {
            Toast.makeText(this,"NAME OR TYPE IS EMPTY",Toast.LENGTH_SHORT).show();
            return;
        }
        UserInformation userInformation = new UserInformation(name,position);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this, "儲存資料中...", Toast.LENGTH_LONG).show();
    }*/

    }

}