package inspire2connect.inspire2connect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Government_Updates_Adapter extends RecyclerView.
        Adapter<Government_Updates_Adapter.MyViewHolder> {
    private static MyClickListener myClickListener;
    Context context;
    Float X, Y;
    private ArrayList<Boolean> play_pause_list = new ArrayList<Boolean>();
    private ArrayList<custom_media_Class> media_player_list = new ArrayList<>();
    private ArrayList<myth_single_object> List;

    public Government_Updates_Adapter(Context context, ArrayList<myth_single_object> List) {
        this.context = context;
        this.List = List;
    }

    public ArrayList<custom_media_Class> getMedia_player_list() {
        return media_player_list;
    }

    public ArrayList<myth_single_object> getResult() {
        return List;
    }

    public void share(String toShare) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        Log.d("sharing", toShare);
        Spanned shareBody = Html.fromHtml(toShare);
        String share = shareBody.toString();
        //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, share);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        Government_Updates_Adapter.myClickListener = myClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.myths_2, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final myth_single_object movie = List.get(position);
        holder.title.setText(Html.fromHtml(movie.getTitle()));
        holder.actual_text.setText(movie.getMyth());
        holder.play_pause.setBackgroundResource(R.drawable.ic_play_arrow_black_34dp);
        play_pause_list.add(false);
        media_player_list.add(new custom_media_Class(null, true));
        holder.share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("sharing", "share clicked");
                share(movie.getTitle());
            }
        });
        holder.play_pause.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("Testing", "Card" + position + "clicked");
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
//                        Log.d("On_Click_DOWN",Float.toString(motionEvent.getX())+" "+Float.toString(motionEvent.getY()));
//                        X = motionEvent.getX();
//                        Y = motionEvent.getY();
//                        if (motionEvent.getX() > 820.0)
//                        {
                        for (int i = 0; i < media_player_list.size(); i++) {
                            if (i != position) {
                                MediaPlayer temp = media_player_list.get(i).getMediaPlayer();
                                if (temp != null && temp.isPlaying()) {
                                    temp.seekTo(temp.getDuration());
                                }
                            }
                        }
                        Log.d("Testing", "Button Touched");
                        if (media_player_list.get(position).getPaused()) {
                            holder.play_pause.setImageDrawable(null);
                            holder.play_pause.setBackgroundResource(R.drawable.ic_pause_black_34dp);
                            //media_player_list.get(position).setPaused(false);
                            MediaPlayer temp = media_player_list.get(position).getMediaPlayer();
                            try {
                                if (false) {
                                } else {
                                    if (temp == null) {
                                        temp = new MediaPlayer();
                                        media_player_list.get(position).setMediaPlayer(temp);
                                        Log.d("Testing", "Step1" + List.get(position).getAudio_url());
                                        temp.setDataSource(List.get(position).getAudio_url());
                                        temp.prepare();
                                        temp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mediaPlayer) {
                                                mediaPlayer.start();
                                                media_player_list.get(position).setPaused(false);
                                            }
                                        });
                                        temp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                            @Override
                                            public void onCompletion(MediaPlayer mediaPlayer) {
                                                Log.d("Testing", "Media Player finished");
                                                media_player_list.get(position).setPaused(true);
                                                holder.play_pause.setBackgroundResource(R.drawable.ic_play_arrow_black_34dp);
                                                mediaPlayer = null;
                                            }
                                        });
                                    } else {
                                        Log.d("Testing", "In here not null");
                                        if (media_player_list.get(position).getPaused()) {
                                            temp.seekTo(media_player_list.get(position).getCurrent_time());
                                            temp.start();
                                            media_player_list.get(position).setPaused(false);
                                        } else {
                                            temp.setDataSource(List.get(position).getAudio_url());
                                            temp.prepare();
                                            temp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                @Override
                                                public void onPrepared(MediaPlayer mediaPlayer) {
                                                    mediaPlayer.start();
                                                    media_player_list.get(position).setPaused(false);
                                                }
                                            });
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("Testing", "Exception", e);
                                e.printStackTrace();
                            }
                        } else {
                            holder.play_pause.setBackgroundResource(R.drawable.ic_play_arrow_black_34dp);
                            //play_pause_list.set(position,false);
                            //media_player_list.get(position).setPaused(true);
                            MediaPlayer temp = media_player_list.get(position).getMediaPlayer();
                            if (temp != null) {
                                if (temp.isPlaying()) {
                                    temp.pause();
                                    Log.d("Testing", "Step2");
                                    media_player_list.get(position).setPaused(true);
                                    media_player_list.get(position).setCurrent_time(temp.getCurrentPosition());
                                }
                            }
                        }
//                        }
                    }
                }
                return false;
            }

        });
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView actual_text;
        public LinearLayout main_layout;
        public ImageView play_pause, share_button;
        public CardView cardView;
        private LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.myth_title);
            actual_text = view.findViewById(R.id.actual_text);
            title.setMovementMethod(LinkMovementMethod.getInstance());
            play_pause = view.findViewById(R.id.play_pause_myth);
            main_layout = view.findViewById(R.id.main_layout);
            cardView = view.findViewById(R.id.cardView);
            title.setOnClickListener(this);
            actual_text.setOnClickListener(this);
            share_button = view.findViewById(R.id.share_button);
            //cardView.setOnClickListener(this);
            //linearLayout=(LinearLayout)itemView.findViewById(R.id.Linear_layout);
        }

        //public CardView guideline_cv;
        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}