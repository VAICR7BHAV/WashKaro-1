package inspire2connect.inspire2connect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

import inspire2connect.inspire2connect.utils.BaseActivity;
import inspire2connect.inspire2connect.utils.LocaleHelper;

public class governmentUpdatesActivity extends BaseActivity implements Serializable {
    public ArrayList<myth_single_object> result;
    DatabaseReference dref;
    DatabaseReference d;
    TextView centre;
    private RecyclerView recyclerView;
    private Government_Updates_Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        ArrayList<custom_media_Class> media_player_list = new ArrayList<>();
        if (mAdapter != null)
            media_player_list = mAdapter.getMedia_player_list();
        if (media_player_list != null)
            for (int i = 0; i < media_player_list.size(); i++) {
                if (media_player_list.get(i).getMediaPlayer() != null) {
                    if (media_player_list.get(i).getMediaPlayer().isPlaying()) {
                        media_player_list.get(i).getMediaPlayer().stop();
                        media_player_list.get(i).getMediaPlayer().seekTo(media_player_list.get(i).getMediaPlayer().getDuration());
                    }
                }
            }
        finish();
        Log.d("Testing", "Sizze of Media player list=" + media_player_list.size());
    }

    private void setGuidelinesHindi() {
//        centre = (TextView) findViewById(R.id.centre_view_gov_updates);
//        centre.setText("सरकारी जानकारी");
        d = FirebaseDatabase.getInstance().getReference();
        dref = FirebaseDatabase.getInstance().getReference().child("Coronavirus").child("Government");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<myth_single_object> result = new ArrayList<>();
                String guidelnines = "";
                int count = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    count += 1;
                    String date = snapshot.child("Date").getValue(String.class);
                    String hindi_title = snapshot.child("Title_hin").getValue(String.class);
                    String hindi_text = snapshot.child("Text_hin").getValue(String.class);
                    String sno = snapshot.child("Sno").getValue().toString();
                    String audio_url = snapshot.child("Audio").getValue(String.class);
                    String redirect_url = snapshot.child("Source").getValue(String.class);

                    //hindi_title="<b>" + sno + ". " + hindi_title + "</b><br>"+date;
                    //String ttd = "<b>" + sno + ". " + date + "<br>" + hindi_title + "</b><br>" + english_text;
                    //String text = hindi_text+ "<br><a href=" + redirect_url + ">स्रोत:" + redirect_url + "</a>";
                    hindi_title = "<b>" + sno + ". " + hindi_title + "</b><br>" + date;//"<br><a href=" + redirect_url + ">स्रोत"+ "</a>";
                    String text = hindi_text;
                    result.add(new myth_single_object(hindi_title,
                            text, Integer.toString(count), audio_url, redirect_url));
                    //String ttd = "<b>" + sno + ". " + date + "<br>" + hindi_title + "</b><br>" + hindi_text;
                    //Spanned text = Html.fromHtml(ttd +
                    //      "<a href=" + redirect_url + "> स्रोत:" + redirect_url + "</a>");
                    //result.add(new myth_single_object(hindi_title,ttd +
                    //      "<a href=" + redirect_url + "> स्रोत:" + redirect_url + "</a>", Integer.toString(count), audio_url));
                }
                populate_recycler_view(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setGuidelinesEnglish() {

//        centre = (TextView) findViewById(R.id.centre_view_gov_updates);
//        centre.setTypeface(null, Typeface.BOLD);

//        centre.setText("Government Updates");
//        FirebaseApp.initializeApp(this);

        d = FirebaseDatabase.getInstance().getReference();
        dref = FirebaseDatabase.getInstance().getReference().child("Coronavirus").child("Government");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                result = new ArrayList<>();
                String guidelnines = "";
                int count = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    count += 1;
                    String date = snapshot.child("Date").getValue(String.class);
                    String english_title = snapshot.child("Title_en").getValue(String.class);
                    String english_text = snapshot.child("Text_en").getValue(String.class);
                    String sno = snapshot.child("Sno").getValue().toString();
                    String audio_url = snapshot.child("Audio").getValue(String.class);
                    String redirect_url = snapshot.child("Source").getValue(String.class);
                    //english_title="<b>" + sno + ". " + english_title + "</b><br>"+date;
                    //String ttd = "<b>" + sno + ". " + date + "<br>" + english_title + "</b><br>" + english_text;
                    //String text = english_text+ "<br><a href=" + redirect_url + "> Source:" + redirect_url + "</a>";
                    english_title = "<b>" + sno + ". " + english_title + "</b><br>" + date;//+ "<br><a href=" + redirect_url + "> Source"+ "</a>";
                    //String ttd = "<b>" + sno + ". " + date + "<br>" + english_title + "</b><br>" + english_text;
                    String text = english_text;//+ "<br><a href=" + redirect_url + "> Source:" + redirect_url + "</a>";

                    result.add(new myth_single_object(english_title,
                            text, Integer.toString(count), audio_url, redirect_url));
                }
                populate_recycler_view(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void populate_recycler_view(ArrayList<myth_single_object> result) {
        mAdapter = new Government_Updates_Adapter(this, result);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));

        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government__updates);

        result = new ArrayList<>();
        result.add(new myth_single_object("Under Maintainence", "Under Maintainence", "1", "Under", "under"));
        mAdapter = new Government_Updates_Adapter(this, result);
        recyclerView = findViewById(R.id.recyclerView_gov_updates);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.govt_updates_act);

        switch (getCurLang()) {
            case englishCode:
                setGuidelinesEnglish();
                break;
            case hindiCode:
                setGuidelinesHindi();
                break;
        }

//        lang_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
//            {
//                if(b)
//                {
//                    Toast.makeText(Government_Updates.this,"English selected",Toast.LENGTH_SHORT).show();
//                    centre.setText("Governemnt UPDATES");
//                    setGuidelinesEnglish();
//                }
//                else
//                {
//                    Toast.makeText(Government_Updates.this,"Hindi selected",Toast.LENGTH_SHORT).show();
//                    centre.setText("सरकारी जानकारी");
//                    setGuidelinesHindi();
//                }
//            }
//        });
        //populate_recycler_view(temp);
    }

    @Override
    public boolean onSupportNavigateUp() {
        ArrayList<custom_media_Class> media_player_list = new ArrayList<>();
        if (mAdapter != null)
            media_player_list = mAdapter.getMedia_player_list();
        if (media_player_list != null)
            for (int i = 0; i < media_player_list.size(); i++) {
                if (media_player_list.get(i).getMediaPlayer() != null) {
                    if (media_player_list.get(i).getMediaPlayer().isPlaying()) {
                        media_player_list.get(i).getMediaPlayer().stop();
                        media_player_list.get(i).getMediaPlayer().seekTo(media_player_list.get(i).getMediaPlayer().getDuration());
                    }
                }
            }
        finish();
        //return super.onSupportNavigateUp();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.lang_togg_butt) {
            toggleLang(this);
        } else if (id == R.id.Survey) {
            Intent i = new Intent(governmentUpdatesActivity.this, maleFemaleActivity.class);
            startActivity(i);
        } else if (id == R.id.developers) {
            Intent i = new Intent(governmentUpdatesActivity.this, aboutActivity.class);
            startActivity(i);
        } else if (id == R.id.privacy_policy) {
            Intent i = new Intent(governmentUpdatesActivity.this, privacyPolicyActivity.class);
            startActivity(i);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.setOnItemClickListener(new Government_Updates_Adapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("Testing", " Clicked on Item gov_updates " + position);
                Intent i = new Intent(governmentUpdatesActivity.this, detailedViewActivity.class);
                //Log.d("Testing",result.get(position).getTitle());
                ArrayList<myth_single_object> result_from_adapter = mAdapter.getResult();
                Log.d("Testing", result_from_adapter.get(position).getTitle());
                /*if(mMediaPlayer!=null)
                {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer=null;
                }*/
                ArrayList<myth_single_object> single = new ArrayList<>();
                single.add(result_from_adapter.get(position));
                //Log.d("Testing",single.get(0).getTitle());
                i.putExtra("detailed_title", single.get(0).getTitle());
                i.putExtra("detailed_text", single.get(0).getMyth());
                i.putExtra("url", single.get(0).getAudio_url());
                i.putExtra("redirect_url", single.get(0).getRedirect_url());
                //i.putExtra("result_list",single);
                startActivity(i);
            }
        });
    }
}