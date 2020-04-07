package inspire2connect.inspire2connect;


//import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class about extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<AboutModel> imageModelArrayList;
    private AboutAdapter adapter;

    @Override
    public boolean onSupportNavigateUp() {

        finish();
        //return super.onSupportNavigateUp();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        imageModelArrayList = eatFruits();
        adapter = new AboutAdapter(this, imageModelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,0));
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<AboutModel> eatFruits() {

        /*
        int[] myImageList = new int[]{R.drawable.kanav_image, R.drawable.rohan_image,R.drawable.vaibhav_image};
        String[] NameList = new String[]{"Kanav Bhagat","Rohan Pandey" ,"Vaibhav Gautam"};
        String[] GithubList=new String[]{"Github\nhttps://github.com/kanavbhagat","Github\nhttps://github.com/rohanpandey","Github\nhttps://github.com/VAICR7BHAV"};
        String[] LinkedInList=new String[]{"LinkedIn\nhttp://linkedin.com/in/kanav-bhagat-133229130","LinkedIn\nhttps://www.linkedin.com/in/rohan-pandey-145170175/","LinkedIn\nhttps://www.linkedin.com/in/vaibhav-gautam-171775187/"};
        String[] TwitterList=new String[]{"Twitter\nhttps://mobile.twitter.com/BhagatKanav","Twitter\nhttps://twitter.com/therohanpandey1","Twitter\nhttps://twitter.com/VAIBHAVGAUTAM13"};
        String[] EmailList=new String[]{"Email\nkanav16046@iiitd.ac.in","Email\nrohan99pandey@gmail.com","Email\nvg459@snu.edu.in"};
        */
        //int[] myImageList = new int[]{R.drawable.logo, R.drawable.tavsir, R.drawable.kanav_image, R.drawable.rohan_image, R.drawable.vaibhav_image, R.drawable.himanshu};
        String[] NameList = new String[]{"TAVLAB", "Dr. Tavpritesh Sethi\n(Mentor)", "Rohan Pandey\n(Developer)", "Vaibhav Gautam\n(Developer)", "Himanshu Sharma\n(Developer)", "Kanav Bhagat\n(Developer)"};
        String[] GithubList = new String[]{"https://github.com/tavlab-iiitd", "https://www.google.com", "https://github.com/rohanpandey", "https://github.com/VAICR7BHAV", "https://github.com/hspandit", "https://github.com/kanavbhagat"};
        String[] LinkedInList = new String[]{"https://tavlab.iiitd.edu.in/", "https://www.linkedin.com/in/tavpritesh/", "https://www.linkedin.com/in/rohan-pandey-145170175/", "https://www.linkedin.com/in/vaibhav-gautam-171775187/", "https://www.linkedin.com/in/himanshu-sharma2950/", "http://linkedin.com/in/kanav-bhagat-133229130"};
        String[] TwitterList = new String[]{"https://www.google.com", "https://mobile.twitter.com/Tavpritesh", "https://mobile.twitter.com/therohanpandey1", "https://mobile.twitter.com/VAIBHAVGAUTAM13", "https://mobile.twitter.com/himanshu9132", "https://mobile.twitter.com/BhagatKanav"};
        String[] EmailList = new String[]{"Email\ntavlabiiitd@gmail.com", "Email\ntavpriteshsethi@iiitd.ac.in", "Email\nrohan99pandey@gmail.com", "Email\nvg459@snu.edu.in", "Email\n 0hspandit0@gmail.com", "Email\nkanav16046@iiitd.ac.in"};


        ArrayList<AboutModel> list = new ArrayList<>();
        for (int i = 0; i < NameList.length; i++) {
            AboutModel fruitModel = new AboutModel();
            fruitModel.setName(NameList[i]);
            //fruitModel.setImage_drawable(myImageList[i]);
            fruitModel.setEmail(EmailList[i]);
            fruitModel.setGithub(GithubList[i]);
            fruitModel.setLinkedin(LinkedInList[i]);
            fruitModel.setTwitter(TwitterList[i]);
            list.add(fruitModel);
        }
        return list;
    }

}