package com.lifeline.fyp.fyp.socialMedia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.classes.NewsFeedAdapter;
import com.lifeline.fyp.fyp.models.AddComment;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.AllCommentsOuterResponse;
import com.lifeline.fyp.fyp.retrofit.responses.InnerArrayAllComments;
import com.lifeline.fyp.fyp.retrofit.responses.PostAllLikes;
import com.lifeline.fyp.fyp.retrofit.responses.PostLikesInner;
import com.lifeline.fyp.fyp.retrofit.responses.SpecificPostOuter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpecificNotification extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private Dialog dialog;
    public ArrayList<SearchFriendsObject> friends;
    public ArrayList<CommentHistory> comments;

    Retrofit retrofit;
    Api api;
    int postId;
    RelativeLayout withtext, withpic;
    String[] seperator, innerseperator, inner2;
    CircleImageView userdp, commentedby;
    TextView name, textofthepost, date, time, numoflikes, commentedbyname, commentcontent;
    ImageView image;
    Button like, comment, share, unlike;

    CircleImageView userdp2;
    TextView name2, textofthepost2, date2, time2, numoflikes2, commentedbyname2;
    Button like2, comment2, share2, unlike2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_specific_notification );

        userdp = (CircleImageView) findViewById( R.id.userimage );
        commentedby = (CircleImageView) findViewById( R.id.commentedbyimage );
        name = (TextView) findViewById( R.id.name );
        commentedbyname = (TextView) findViewById( R.id.commentedbyname );
        commentcontent = (TextView) findViewById( R.id.commentedcontent );
        textofthepost = (TextView) findViewById( R.id.textofpost );
        image = (ImageView) findViewById( R.id.picofthepost );
        like = (Button) findViewById( R.id.like );
        unlike = (Button) findViewById( R.id.unlike );
        comment = (Button) findViewById( R.id.comment );
        share = (Button) findViewById( R.id.share );
        date = (TextView) findViewById( R.id.date );
        time = (TextView) findViewById( R.id.time );
        numoflikes = (TextView) findViewById( R.id.numoflikes );

        userdp2 = (CircleImageView) findViewById( R.id.userimage2 );
        name2 = (TextView) findViewById( R.id.name2 );
        commentedbyname2 = (TextView) findViewById( R.id.commentedbyname2 );
        textofthepost2 = (TextView) findViewById( R.id.textofpost2 );
        like2 = (Button) findViewById( R.id.like2 );
        unlike2 = (Button) findViewById( R.id.unlike2 );
        comment2 = (Button) findViewById( R.id.comment2 );
        share2 = (Button) findViewById( R.id.share2 );
        date2 = (TextView) findViewById( R.id.date2 );
        time2 = (TextView) findViewById( R.id.time2 );
        numoflikes2 = (TextView) findViewById( R.id.numoflikes2 );

        withpic = (RelativeLayout) findViewById( R.id.withpic );
        withtext = (RelativeLayout) findViewById( R.id.withtext );


        retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        api = retrofit.create( Api.class );

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postId = extras.getInt( "postId" ); // information contains boolean+gender+age+lifestylecategory.

        }

        gettingSpecificPost( postId );


    }

    private void gettingSpecificPost(final Integer postId) {

        final ProgressDialog dialog=new ProgressDialog(SpecificNotification.this);
        dialog.setCancelable( false );
        dialog.setMessage( "Loading..." );
        dialog.show();

        Call<SpecificPostOuter> specificPostOuterCall = api.specificpost( postId );
        specificPostOuterCall.enqueue( new Callback<SpecificPostOuter>() {
            @Override
            public void onResponse(Call<SpecificPostOuter> call, final Response<SpecificPostOuter> response) {

                Log.d( "RESPOBSECECKINFNEW", String.valueOf( response ) );
                Log.d( "RESPOBSECECKINFNEW", String.valueOf( response.body().getData().getHasImage() ) );

                if (response.body().getData().getHasImage().equals( "true" )) {

                    withpic.setVisibility( View.VISIBLE );

                    textofthepost.setText( response.body().getData().getContent() );
                    Glide.with( SpecificNotification.this )
                            .load( response.body().getData().getMember().getProfilePicture() ).into( userdp );
                    Glide.with( SpecificNotification.this )
                            .load( response.body().getData().getPictureLink() ).into( image );

                    Log.d( "Username","sdfsdrfed" );
                    name.setText( response.body().getData().getMember().getUsername() );
                    numoflikes.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (response.body().getData().getNoOfLikes().trim().equals( "0" )) {
                                Toast.makeText( SpecificNotification.this,
                                        "No likes", Toast.LENGTH_SHORT ).show();
                            } else {
                                displayingnumoflikes(postId);                            }
                        }
                    } );
                    numoflikes.setText( response.body().getData().getNoOfLikes() + " likes" );

                    if (response.body().getData().getNoOfComments().trim().equals( "0" )) {
                        commentedbyname.setText( "No comment on in this post" );
                        commentedbyname.setEnabled( false );
                    } else {
                        commentedbyname.setText( "View all " + response.body().getData().getNoOfComments() + " comments" );
                        commentedbyname.setEnabled( true );
                    }

                    commentedbyname.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displayingAllPostComments(postId);

                        }
                    } );

                    comment.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            postingComment(response.body().getData().getMemberId(),postId);
                        }
                    } );
                    seperator = response.body().getData().getTime().split( "T" );
                    innerseperator = seperator[0].split( "-" );
                    inner2 = seperator[1].split( ":" );
                    date.setText( innerseperator[2] + "/" + innerseperator[1] + "/" + innerseperator[0] );
                    time.setText( inner2[0] + ":" + inner2[1] );

                    dialog.hide();

                } else {
                    withtext.setVisibility( View.VISIBLE );

                    textofthepost2.setText( response.body().getData().getContent() );
                    Glide.with( SpecificNotification.this )
                            .load( response.body().getData().getMember().getProfilePicture() ).into( userdp2 );
                    name2.setText( response.body().getData().getMember().getUsername() );
                    numoflikes2.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (response.body().getData().getNoOfLikes().trim().equals( "0" )) {
                                Toast.makeText( SpecificNotification.this,
                                        "No likes", Toast.LENGTH_SHORT ).show();
                            } else {
                                displayingnumoflikes(postId);                            }
                        }
                    } );
                    numoflikes2.setText( response.body().getData().getNoOfLikes() + " likes" );

                    if (response.body().getData().getNoOfComments().trim().equals( "0" )) {
                        commentedbyname2.setText( "No comment on in this post" );
                        commentedbyname2.setEnabled( false );
                    } else {
                        commentedbyname2.setText( "View all " + response.body().getData().getNoOfComments() + " comments" );
                        commentedbyname2.setEnabled( true );
                    }

                    commentedbyname2.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displayingAllPostComments( postId );
                        }
                    } );
                    comment2.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            postingComment(response.body().getData().getMemberId(),postId);
                        }
                    } );
                    seperator = response.body().getData().getTime().split( "T" );
                    innerseperator = seperator[0].split( "-" );
                    inner2 = seperator[1].split( ":" );
                    date2.setText( innerseperator[2] + "/" + innerseperator[1] + "/" + innerseperator[0] );
                    time2.setText( inner2[0] + ":" + inner2[1] );
dialog.hide();
                }
            }

            @Override
            public void onFailure(Call<SpecificPostOuter> call, Throwable t) {

            }
        } );
    }


    private void displayingnumoflikes(final Integer PostId) {
        friends = new ArrayList<>();
Log.d( "sdfsdfdwwwqq","OPebibng" );

        dialog = new Dialog( SpecificNotification.this );
        builder = new AlertDialog.Builder( SpecificNotification.this );
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View customView = inflater.inflate( R.layout.postalllikes, null );
        builder.setTitle( "Liked by" );

        final RecyclerView recyclerview = (RecyclerView) customView.findViewById( R.id.recyclerview );


        Call<PostAllLikes> postAllLikesCall = api.postAllLikes( PostId );
        postAllLikesCall.enqueue( new Callback<PostAllLikes>() {
            @Override
            public void onResponse(Call<PostAllLikes> call, Response<PostAllLikes> response) {
                Log.d( "alalalal", String.valueOf( response ) );

                List<PostLikesInner> searchFriendsObjects = response.body().getData();

                for (int i = 0; i < searchFriendsObjects.size(); i++) {
                    friends.add( new SearchFriendsObject(
                            response.body().getData().get( i ).getMember().getMemberId(),
                            response.body().getData().get( i ).getMember().getUsername(),
                            response.body().getData().get( i ).getMember().getEmail(),
                            response.body().getData().get( i ).getMember().getProfilePicture()

                    ) );

                }

                LinearLayoutManager dpm = new LinearLayoutManager( SpecificNotification.this, LinearLayoutManager.VERTICAL, false );
                RecyclerView.Adapter freindsrecycleradapter = new LikeFriendAdapter( friends, SpecificNotification.this, PostId );
                recyclerview.setAdapter( freindsrecycleradapter );
                recyclerview.setLayoutManager( dpm );
                dialog.show();
            }

            @Override
            public void onFailure(Call<PostAllLikes> call, Throwable t) {

            }
        } );


        builder.setView( customView );
        dialog = builder.create();


    }
    private void postingComment(final Integer memberId, final Integer postId) {
        dialog = new Dialog( SpecificNotification.this );
        builder = new AlertDialog.Builder( SpecificNotification.this );
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );


        //this is custom dialog
        //custom_popup_dialog contains textview only
        View customView = inflater.inflate( R.layout.statusposting, null );
        // reference the textview of custom_popup_dialog

        Button posting = (Button) customView.findViewById( R.id.postthestatus );
        posting.setText( "Posting Comment !" );
        final EditText editText = (EditText) customView.findViewById( R.id.textstatus );
        builder.setTitle( "What's in your mind?" );

        posting.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty( editText.getText().toString() )) {
                    Toast.makeText( SpecificNotification.this, "Write some text to continue", Toast.LENGTH_LONG ).show();
                } else {

                    sendingCommenttoserverText(editText.getText().toString(), postId, memberId );
                }
                // api call first then in that call close the dialog.

            }
        } );

        builder.setView( customView );
        dialog = builder.create();
        dialog.show();

    }
    private void sendingCommenttoserverText( final String text, Integer postId, final Integer memberId) {
        Log.d( "ASdasdassdChecking", String.valueOf( memberId ) );
        AddComment addComment = new AddComment( postId, memberId, text );

        Call<AddComment> addCommentCall = api.addingComment( addComment );
        addCommentCall.enqueue( new Callback<AddComment>() {
            @Override
            public void onResponse(Call<AddComment> call, Response<AddComment> response) {

                Toast.makeText( SpecificNotification.this, "Comment Posted Sucessfully!", Toast.LENGTH_SHORT ).show();
            dialog.dismiss();
            }


            @Override
            public void onFailure(Call<AddComment> call, Throwable t) {

            }
        } );


    }

    private void displayingAllPostComments(Integer postId) {
        comments = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        Api api = retrofit.create( Api.class );


        dialog = new Dialog( SpecificNotification.this );
        builder = new AlertDialog.Builder( SpecificNotification.this );
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View customView = inflater.inflate( R.layout.postalllikes, null );
        builder.setTitle( "Comments are" );

        final RecyclerView recyclerview = (RecyclerView) customView.findViewById( R.id.recyclerview );

        final Call<AllCommentsOuterResponse> allCommentsOuterResponseCall = api.commentsall( postId );
        allCommentsOuterResponseCall.enqueue( new Callback<AllCommentsOuterResponse>() {
            @Override
            public void onResponse(Call<AllCommentsOuterResponse> call, Response<AllCommentsOuterResponse> response) {

                List<InnerArrayAllComments> allCommentsOuterResponses = response.body().getData();

                for (int i = 0; i < allCommentsOuterResponses.size(); i++) {
                    comments.add( new CommentHistory( response.body().getData().get( i ).getMember().getUsername(),
                            response.body().getData().get( i ).getMember().getProfilePicture(),
                            response.body().getData().get( i ).getContent() ) );


                }

                LinearLayoutManager dpm = new LinearLayoutManager( SpecificNotification.this, LinearLayoutManager.VERTICAL, false );
                RecyclerView.Adapter freindsrecycleradapter = new CommentsAdapter( comments, SpecificNotification.this );
                recyclerview.setAdapter( freindsrecycleradapter );
                recyclerview.setLayoutManager( dpm );
                dialog.show();
            }

            @Override
            public void onFailure(Call<AllCommentsOuterResponse> call, Throwable t) {

            }
        } );


        builder.setView( customView );
        dialog = builder.create();
    }

}