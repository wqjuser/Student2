package cn.hsd.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.hsd.student.R;
import cn.hsd.student.activity.Json.json;
import cn.hsd.student.activity.StudentMain.StudentActivity;
import cn.hsd.student.activity.login.Mycontents;
import cn.hsd.student.activity.login.SpTools;
import cn.hsd.student.activity.model.YA_Login_model;
import cn.hsd.student.activity.service.SDS_Httpclient;
import cn.hsd.student.activity.service.SDS_ZMHandler;

public class wqj_YA_LoginActivity extends AppCompatActivity {
    private Button wqj_ya_bt;
    private TextView tv1;
    private TextView tv2;
    private String username;
    private String password;
    SDS_ZMHandler handler = new SDS_ZMHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);//content return data(json);

            if ("1".equals(content)) {
                SpTools.getBooleans(getApplicationContext(), Mycontents.ISSETUP, false);
                Toast.makeText(wqj_YA_LoginActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(wqj_YA_LoginActivity.this, StudentActivity.class);
                startActivity(intent);


            } else {
                SpTools.setBooleans(getApplicationContext(), Mycontents.ISSETUP, true);
                Toast.makeText(wqj_YA_LoginActivity.this,"登陆成功",Toast.LENGTH_LONG).show();

            }

        }

        @Override
        public void onFailture(String content) {
            Toast.makeText(wqj_YA_LoginActivity.this, "登陆失败，请检查你的身份证号和密码并重新登录", Toast.LENGTH_LONG).show();
            super.onFailture(content);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ya__login);
        wqj_ya_bt=(Button)findViewById(R.id.wqj_ya_log_button);
        tv1=(TextView)findViewById(R.id.username_edit);
        tv2=(TextView)findViewById(R.id.password_edit);
        wqj_ya_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv1.length() == 0 && tv2.length() == 0) {
                    Toast.makeText(wqj_YA_LoginActivity.this,"身份证号或密码不能为空",Toast.LENGTH_LONG).show();

                }
                else if (tv1.length()<13){
                    Toast.makeText(wqj_YA_LoginActivity.this,"身份证号错误或者格式不正确",Toast.LENGTH_LONG).show();
                }
                else if (tv2.length()<6){
                    Toast.makeText(wqj_YA_LoginActivity.this,"密码错误",Toast.LENGTH_LONG).show();

                }else {


                    new Thread() {
                        public void run() {
                            String path = "http://192.168.1.100:8080/WQJServices/TestServlet";
                            username = tv1.getText().toString().trim();
                            password = tv2.getText().toString().trim();
                            YA_Login_model log_data = new YA_Login_model();
                            log_data.setUsername(username);
                            log_data.setPassword(password);
                            json<YA_Login_model> json = new json<YA_Login_model>();
                            String infoResult = json.ObjectToJson1(log_data);
                            SDS_Httpclient conn = new SDS_Httpclient();
                            conn.Postclient(path, infoResult,handler);

                        }
                    }.start();
                }
            }
        });

    }
   /* public class StuBtuOnClickListener implements View.OnClickListener{
        public void onClick(View v){
            if(tv2.length()==0||tv1.length()==0){
                Toast.makeText(wqj_YA_LoginActivity.this,"学号或密码不能为空",Toast.LENGTH_LONG).show();
            }
            else if(tv1.length()<10){
                Toast.makeText(wqj_YA_LoginActivity.this,"学号格式错误或者长度不够",Toast.LENGTH_LONG).show();
            }
            else if(tv2.length()<6){
                Toast.makeText(wqj_YA_LoginActivity.this,"密码错误",Toast.LENGTH_LONG).show();
            }

        }
    }
*/
}
