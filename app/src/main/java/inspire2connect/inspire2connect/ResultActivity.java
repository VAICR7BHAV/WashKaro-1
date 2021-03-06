package inspire2connect.inspire2connect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import inspire2connect.inspire2connect.utils.BaseActivity;
import inspire2connect.inspire2connect.utils.LocaleHelper;

public class ResultActivity extends BaseActivity {
    TextView tv, tv2;
    Button btnRestart;
    DatabaseReference ref;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(getApplicationContext(), homeActivity.class);
        startActivity(in);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {

        Intent in = new Intent(getApplicationContext(), homeActivity.class);
        startActivity(in);
        finish();
        //return super.onSupportNavigateUp();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ref = FirebaseDatabase.getInstance().getReference();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv = findViewById(R.id.prediction);
        tv2 = findViewById(R.id.nameDisp);
        btnRestart = findViewById(R.id.backbutton);

        StringBuffer sb = new StringBuffer();
        int[] ans = QuestionsActivity.ans;
        boolean val = true;
//        if(ans[3]==2)
//        {
//            val = false;
//        }
//        else if(ans[4]==2)
//        {
//            val = false;
//        }
//        else if(ans[0]==2)
//        {
//            if(ans[1]==2)
//            {
//                if(ans[5]==2)
//                {
//                    val = false;
//                }
//                else
//                    {
//                    if(ans[6]==1)
//                    {
//                        val = false;
//                    }
//                }
//            }
//        }
//
        //ref.child("user_symptom_data").child(key).child("number_of_relevant_votes").setValue(relevant_num + 1);
        String to_push = "";
        for (int i = 0; i < ans.length; i++) {
            to_push += ans[i] + " ";
        }
        ref.child("user_symptom_data").push().setValue(to_push);

        boolean ARI = false;
        if (ans[2] == 2) {
            val = false;
        } else if (ans[4] == 2) {
            if (ans[3] == 2) {
                val = false;
            } else if (ans[3] == 1) {
                ARI = true;
            }
        } else if (ans[4] == 1)
            ARI = true;
        if (ARI) {
            if (ans[0] == 1) {
                val = true;
            } else if (ans[1] == 1) {
                val = true;
            } else if (ans[5] == 1 && ans[6] == 1) {
                val = false;
            } else if (ans[5] == 1 && ans[6] == 2) {
                val = true;
            }
        }

        if (val) {
            sb.append(getString(R.string.covid_is_present));
        } else {
            sb.append(getString(R.string.covid_is_not_present));
        }

        tv.setText(sb);
        //tv2.setText(QuestionsActivity.myName);

        QuestionsActivity.ans = new int[7];

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), homeActivity.class);
                startActivity(in);
                finish();
            }
        });
    }
}
