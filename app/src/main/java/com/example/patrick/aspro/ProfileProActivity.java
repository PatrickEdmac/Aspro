package com.example.patrick.aspro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.patrick.aspro.models.Contact;

public class ProfileProActivity extends AppCompatActivity {
    private TextView name;
    private TextView prof;
    private TextView desc;
    private Button bt_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pro);

        name = (TextView) findViewById(R.id.tv_profilePro_name);
        prof = (TextView) findViewById(R.id.tv_profilePro_profession);
        desc = (TextView) findViewById(R.id.tv_profilePro_desc);
        bt_msg = (Button) findViewById(R.id.bt_profilePro_msg);

        final Bundle extras = getIntent().getExtras();
        if (extras != null){
            name.setText(extras.getString("name"));
            prof.setText(extras.getString("profession"));
            desc.setText(extras.getString("desc"));
        }

        bt_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //criar contato

                Intent intent = new Intent(ProfileProActivity.this,Chat.class);
                intent.putExtra("name",extras.getString("name"));
                intent.putExtra("uid",extras.getString("uid"));
                startActivity(intent);

            }
        });

    }
}
