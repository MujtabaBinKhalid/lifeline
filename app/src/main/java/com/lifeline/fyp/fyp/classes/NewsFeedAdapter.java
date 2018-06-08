package com.lifeline.fyp.fyp.classes;

/**
 * Created by Mujtaba_khalid on 11/25/2017.
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.style.TabStopSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.lifeline.fyp.fyp.R;
import com.lifeline.fyp.fyp.models.AddComment;
import com.lifeline.fyp.fyp.retrofit.Api;
import com.lifeline.fyp.fyp.retrofit.responses.AllCommentsOuterResponse;
import com.lifeline.fyp.fyp.retrofit.responses.FriendDetailsOuter;
import com.lifeline.fyp.fyp.retrofit.responses.InnerArrayAllComments;
import com.lifeline.fyp.fyp.retrofit.responses.LikeUnLike;
import com.lifeline.fyp.fyp.retrofit.responses.MemberObject;
import com.lifeline.fyp.fyp.retrofit.responses.PostAllLikes;
import com.lifeline.fyp.fyp.retrofit.responses.PostLikesInner;
import com.lifeline.fyp.fyp.retrofit.responses.SearchResultResponse;
import com.lifeline.fyp.fyp.retrofit.responses.SendingRequestResponse;
import com.lifeline.fyp.fyp.socialMedia.CommentHistory;
import com.lifeline.fyp.fyp.socialMedia.CommentsAdapter;
import com.lifeline.fyp.fyp.socialMedia.FetchingNewsFeedData;
import com.lifeline.fyp.fyp.socialMedia.FriendsProfile;
import com.lifeline.fyp.fyp.socialMedia.SearchFriendAdapter;
import com.lifeline.fyp.fyp.socialMedia.SearchFriendsObject;
import com.lifeline.fyp.fyp.socialMedia.LikeFriendAdapter;
import com.lifeline.fyp.fyp.socialMedia.UserProfile;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Target;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String[] seperator, innerseperator, inner2;
    private static final int TYPE_PIC = 1;
    private static final int TYPE_TEXT = 2;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    ProgressDialog instadialog;
    ProgressDialog fbdialog;
    private boolean hello = true;
    private ArrayList<Integer> arrayoflikes = new ArrayList<>(  );
    private int postlikes;
    private Integer activememberId;
    public ArrayList<SearchFriendsObject> friends;
    public ArrayList<CommentHistory> comments;

    public static ArrayList<FetchingNewsFeedData> itemList;
    Context context;
    URL url;
    Uri bmi;
    Bitmap myBitmap;
    Bitmap image;
    boolean firstTime = true;
    int countinglikes;
    ShareDialog shareDialog;
    String classstatus;

    // Constructor of the class
    public NewsFeedAdapter(ArrayList<FetchingNewsFeedData> itemList, Context context, Integer activememberId,String classstatus) {
        this.itemList = itemList;
        this.context = context;
        this.activememberId = activememberId;
        this.classstatus = classstatus;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    // determine which layout to use for the row
    @Override
    public int getItemViewType(int position) {
        FetchingNewsFeedData item = itemList.get( position );
        if (item.getType() == FetchingNewsFeedData.PostType.PICTURE) {
            return TYPE_PIC;
        } else if (item.getType() == FetchingNewsFeedData.PostType.TEXT) {
            return TYPE_TEXT;
        } else {
            return -1;
        }
    }


    // specify the row layout file and click for each row
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PIC) {
            View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.newsfeed, parent, false );
            return new PictureStatus( view );
        } else if (viewType == TYPE_TEXT) {
            View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.newsfeedtext, parent, false );
            return new TextStatus( view );
        } else {
            throw new RuntimeException( "The type has to be ONE or TWO" );
        }
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int listPosition) {
        switch (holder.getItemViewType()) {
            case TYPE_PIC:
                initLayoutOne( (PictureStatus) holder, listPosition );
                Log.d( "Sdfsfsdfs", String.valueOf( listPosition ) );
                break;
            case TYPE_TEXT:
                initLayoutTwo( (TextStatus) holder, listPosition );
                Log.d( "Sdfsfsdfs", String.valueOf( listPosition ) );

                break;
            default:
                break;
        }
    }

    private void initLayoutOne(final PictureStatus holder, final int pos) {

        final FetchingNewsFeedData model = itemList.get( pos );
        holder.name.setText( model.getName() );
        Glide.with( context ).load( model.getPicture() ).into( holder.userdp );
        Glide.with( context ).load( model.getPicturelink() ).into( holder.image );
        holder.textofthepost.setText( model.getContent() );
        seperator = model.getTime().split( "T" );
        innerseperator = seperator[0].split( "-" );
        inner2 = seperator[1].split( ":" );
        holder.date.setText( innerseperator[2] + "/" + innerseperator[1] + "/" + innerseperator[0] );
        holder.time.setText( inner2[0] + ":" + inner2[1] );
        holder.commentcontent.setText( model.getCommentcontent() );

arrayoflikes.add( pos,Integer.parseInt( itemList.get( pos ).getNooflikes()  ));
Log.d( "VALUEOFTHELIKE",arrayoflikes.get( pos )+":: "+ pos );
holder.numoflikes.setText( arrayoflikes.get( pos )+" likes" );
        holder.name.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VisitingFriendProfile( itemList.get( pos ).getMemberId(), itemList.get( pos ).getEmail(), true );
            }
        } );


        if(arrayoflikes.get( pos )== 0){
            holder.numoflikes.setEnabled( false );
        }
        else {
            holder.numoflikes.setEnabled( true );
            holder.numoflikes.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (arrayoflikes.get( pos ) == 0){
                        holder.numoflikes.setEnabled( false );
                    }
                    else{
                        displayingnumoflikes( model.getPostId() );

                    }
                }
            } );
        }


        holder.userdp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VisitingFriendProfile( itemList.get( pos ).getMemberId(), itemList.get( pos ).getEmail(), true );

            }
        } );

        if(classstatus.equals( "user" )){

            holder.share.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //     bothPlatformSharingOption(model.getPicturelink(),"dfdfdf");
                    sharingplatform( model.getPicturelink() );
                    Log.d( "KHOLGYABC", "KHOLGYA" );
                }
            } );

        }
        else {
        holder.share.setVisibility( View.INVISIBLE );
        }
        String status = model.getCommentcontent() + "isnull";

        if (status.equals( "nullisnull" )) {

            holder.comments.setVisibility( View.VISIBLE );
            holder.comments.setText( "No comments" );

        } else if (!model.getCommentcontent().equals( "null" )) {
            holder.comments.setVisibility( View.INVISIBLE );
            holder.commentcontent.setVisibility( View.VISIBLE );
        //    holder.comments.setVisibility( View.INVISIBLE );

            Glide.with( context ).load( model.getCommentbypicturelink() ).into( holder.commentedby );
            holder.commentedbyname.setText( model.getCommentedby() );
            holder.commentcontent.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    displayingAllPostComments( model.getPostId() );
                }
            } );

        }

        holder.comment.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postingCommentForPictureStatus( holder, activememberId, model.getPostId(), pos );
            }
        } );



        if (firstTime) {
            holder.numoflikes.setText( arrayoflikes.get(pos) + " likes" );
            countinglikes = Integer.parseInt( model.getNooflikes() );
            Log.d( "POSTSLIKESCHECKING", String.valueOf( countinglikes ) + "Likes" );
            Log.d( "POSTSLIKESCHECKING", String.valueOf( pos ) );
            if (itemList.get( pos ).isHasLiked()) {
                holder.like.setVisibility( View.INVISIBLE );
                holder.unlike.setVisibility( View.VISIBLE );
            } else {
                holder.like.setVisibility( View.VISIBLE );
                holder.unlike.setVisibility( View.INVISIBLE );
            }
        }
        holder.like.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countinglikes = Integer.parseInt( itemList.get( pos ).getNooflikes() );
                Log.d( "LIKESCHECKIGN", String.valueOf( countinglikes ) );

                Log.d( "LSLLSSL", String.valueOf( countinglikes ) );
                holder.unlike.setVisibility( View.VISIBLE );
                holder.like.setVisibility( View.INVISIBLE );
                holder.numoflikes.setText( arrayoflikes.get( pos )+1 + " likes" );
                hello = false;

                likingPost( activememberId, model.getPostId(),pos );
            }
        } );


        holder.unlike.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d( "LSLLSSL", String.valueOf( countinglikes ) );
                holder.unlike.setVisibility( View.INVISIBLE );
                holder.like.setVisibility( View.VISIBLE );
                hello = false;
                holder.numoflikes.setText( arrayoflikes.get( pos ) - 1 + " likes" );
                unlikingPost( activememberId, model.getPostId(),pos );
            }
        } );


    }

    private void initLayoutTwo(final TextStatus holder, final int pos) {

        final FetchingNewsFeedData model = itemList.get( pos );
        seperator = model.getTime().split( "T" );
        innerseperator = seperator[0].split( "-" );
        holder.name.setText( model.getName() );
        Glide.with( context ).load( model.getPicture() ).into( holder.userdp );
        holder.textofthepost.setText( model.getContent() );
        inner2 = seperator[1].split( ":" );
        holder.date.setText( innerseperator[2] + "/" + innerseperator[1] + "/" + innerseperator[0] );
        holder.time.setText( inner2[0] + ":" + inner2[1] );
        holder.commentcontent.setText( model.getCommentcontent() );


        arrayoflikes.add( pos,Integer.parseInt( itemList.get( pos ).getNooflikes()  ));
        holder.numoflikes.setText( arrayoflikes.get( pos ) + " likes" );

        Log.d( "VALUEOFTHELIKE",arrayoflikes.get( pos )+":: "+ pos );

        holder.name.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VisitingFriendProfile( itemList.get( pos ).getMemberId(), itemList.get( pos ).getEmail(), true );

            }
        } );


        if(arrayoflikes.get( pos )== 0){
            holder.numoflikes.setEnabled( false );
        }
        else {
            holder.numoflikes.setEnabled( true );
            holder.numoflikes.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (arrayoflikes.get( pos ) == 0){
                        holder.numoflikes.setEnabled( false );
                    }
                    else{
                        displayingnumoflikes( model.getPostId() );

                    }
                  }
            } );
        }

        holder.userdp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VisitingFriendProfile( itemList.get( pos ).getMemberId(), itemList.get( pos ).getEmail(), true );

            }
        } );


        if(classstatus.equals( "user" )){
            holder.share.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                fbSharingText(itemList.get( pos ).getContent());
                }
            } );

        }
        else {
            holder.share.setVisibility( View.INVISIBLE );

            holder.share.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            } );

        }


        holder.comment.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postingCommentForTextStatus( holder, activememberId, model.getPostId(), pos );
            }
        } );

        String status = model.getCommentcontent() + "isnull";

        if (status.equals( "nullisnull" )) {

            holder.comments.setVisibility( View.VISIBLE );
            holder.comments.setText( "No comments" );

        } else if (!model.getCommentcontent().equals( "null" )) {
            holder.comments.setVisibility( View.INVISIBLE );
            holder.commentcontent.setVisibility( View.VISIBLE );
           // holder.comments.setVisibility( View.INVISIBLE );

            Glide.with( context ).load( model.getCommentbypicturelink() ).into( holder.commentedby );
            holder.commentedbyname.setText( model.getCommentedby() );

            holder.commentcontent.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    displayingAllPostComments( model.getPostId() );
                }
            } );
        }

        if(holder.numoflikes.getText().toString().equals( "0" )){
            holder.numoflikes.setEnabled( false );
        }
        if (firstTime) {
            holder.numoflikes.setText( arrayoflikes.get( pos ) + " likes" );
            countinglikes = Integer.parseInt( model.getNooflikes() );
            Log.d( "POSTSLIKESCHECKING", String.valueOf( postlikes ) + "likes" );
            Log.d( "POSTSLIKESCHECKING", String.valueOf( pos ) );

            if (itemList.get( pos ).isHasLiked()) {
                holder.like.setVisibility( View.INVISIBLE );
                holder.unlike.setVisibility( View.VISIBLE );
            } else {
                holder.like.setVisibility( View.VISIBLE );
                holder.unlike.setVisibility( View.INVISIBLE );
            }
        }

        holder.like.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d( "LSLLSSL", String.valueOf( countinglikes ) );
                holder.unlike.setVisibility( View.VISIBLE );
                holder.like.setVisibility( View.INVISIBLE );
                holder.numoflikes.setText( arrayoflikes.get( pos ) + 1 + " likes" );
                hello = false;

                likingPost( activememberId, model.getPostId(),pos );
            }
        } );


        holder.unlike.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d( "LSLLSSL", String.valueOf( countinglikes ) );
                holder.unlike.setVisibility( View.INVISIBLE );
                holder.like.setVisibility( View.VISIBLE );
                hello = false;
                holder.numoflikes.setText( arrayoflikes.get( pos ) - 1 + " likes" );
                Log.d( "LlIKEBUTTONSTATUS", String.valueOf( holder.like.isEnabled() ) );
                Log.d( "LIKEBUTTONSTATUS", String.valueOf( holder.unlike.isEnabled() ) );
                Log.d( "LIKEBUTTONSTATUS", String.valueOf( ")))))))))))))))))))" ) );

                unlikingPost( activememberId, model.getPostId(),pos );
            }
        } );


    }

    private void fbSharingText(String text){
        Intent share=new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.setPackage("com.facebook.katana"); //Facebook App package

        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, "sdfsdffsfsffsfsfsfsfsfsfsfsfsf");

        context.startActivity(Intent.createChooser(share, "Share link!"));


    }

    private void VisitingFriendProfile(final Integer friendid, final String email, final boolean areFriends) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();

        Api api = retrofit.create( Api.class );

        Call<FriendDetailsOuter> call = api.getProfile( activememberId, friendid );
        call.enqueue( new Callback<FriendDetailsOuter>() {
            @Override
            public void onResponse(Call<FriendDetailsOuter> call, Response<FriendDetailsOuter> response) {
                String combination = activememberId + ";" + friendid + ";" + email + ";" + areFriends + ";" + response.body().getData().getNoOfFriends() + ";" +
                        response.body().getData().getNoOfPosts() + ";" + response.body().getData().getStatus();


                Log.d( "Sdfsdfererer", String.valueOf( activememberId ) );
                Log.d( "Sdfsdfererer", String.valueOf( friendid ) );

             if(activememberId == friendid){

                 context.startActivity( new Intent( context,UserProfile.class ) );
             }
             else{
                 Intent intent = new Intent( context, FriendsProfile.class );
                 intent.putExtra( "combination", combination );
                 context.startActivity( intent );

             }


            }

            @Override
            public void onFailure(Call<FriendDetailsOuter> call, Throwable t) {

            }
        } );


        // id,friendid,email,posts,friends
    }

    public Bitmap getBitmapFromURL(final String src) {

        Thread thread = new Thread( new Runnable() {

            @Override
            public void run() {
                try {
                    URL url2 = new URL( src );
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput( true );
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream( input );


                } catch (IOException e) {

                }
            }
        } );

        thread.start();
        return myBitmap;
    }

    private void instasharing(final String imageurl) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d( "IMAGEURL", imageurl );
                    url = new URL( imageurl );
                    image = BitmapFactory.decodeStream( url.openConnection().getInputStream() );
                    Uri bmpUri = getImageUri( context, image );
                    instadialog.dismiss();
                    Intent share = new Intent( Intent.ACTION_SEND );

                    share.setPackage( "com.instagram.android" );

                    share.setType( "image/*" );
                    share.putExtra( Intent.EXTRA_STREAM, bmpUri );
                    context.startActivity( Intent.createChooser( share, "Share to" ) );

//
                    Log.d( "IMAGEURL", String.valueOf( image ) );

                } catch (IOException e) {
                    Log.d( "IMAGEURL", String.valueOf( "WQRWWEE" ) );
                    Log.d( "IMAGEURL", String.valueOf( e.getLocalizedMessage() ) );
                    Log.d( "IMAGEURL", String.valueOf( e.getMessage() ) );


                    e.printStackTrace();
                }

            }
        };

        Thread thread = new Thread( runnable );
        thread.start();
        Log.d( "IMAGEURL22", String.valueOf( image ) );

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Log.d( "||||ALALALA", String.valueOf( inImage ) );
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress( Bitmap.CompressFormat.JPEG, 100, bytes );
        String path = MediaStore.Images.Media.insertImage( inContext.getContentResolver(), inImage, "Title", null );
        Log.d( "ALALALAAA|", String.valueOf( Uri.parse( path ) ) );

        return Uri.parse( path );
    }

    private void postingCommentForPictureStatus(final PictureStatus holder, final Integer memberId, final Integer postId, int pos) {
        dialog = new Dialog( context );
        builder = new AlertDialog.Builder( context );
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );


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
                    Toast.makeText( context, "Write some text to continue", Toast.LENGTH_LONG ).show();
                } else {

                    sendingCommenttoserverPicture( holder, editText.getText().toString(), postId, memberId );
                }
                // api call first then in that call close the dialog.

            }
        } );

        builder.setView( customView );
        dialog = builder.create();
        dialog.show();

    }

    private void postingCommentForTextStatus(final TextStatus holder, final Integer memberId, final Integer postId, int pos) {
        dialog = new Dialog( context );
        builder = new AlertDialog.Builder( context );
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );


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
                    Toast.makeText( context, "Write some text to continue", Toast.LENGTH_LONG ).show();
                } else {

                    sendingCommenttoserverText( holder, editText.getText().toString(), postId, memberId );
               holder.comments.setVisibility( View.INVISIBLE );
                }
                // api call first then in that call close the dialog.

            }
        } );

        builder.setView( customView );
        dialog = builder.create();
        dialog.show();

    }

    private void sendingCommenttoserver(String text, Integer postId, Integer memberId) {
        Log.d( "ASdasdassdChecking", String.valueOf( memberId ) );
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        Api api = retrofit.create( Api.class );

        AddComment addComment = new AddComment( postId, memberId, text );

        Call<AddComment> addCommentCall = api.addingComment( addComment );
        addCommentCall.enqueue( new Callback<AddComment>() {
            @Override
            public void onResponse(Call<AddComment> call, Response<AddComment> response) {
                Log.d( "Responsesesse", String.valueOf( response ) );
                dialog.dismiss();
                Toast.makeText( context, "Comment Posted Sucessfully!", Toast.LENGTH_SHORT ).show();

            }


            @Override
            public void onFailure(Call<AddComment> call, Throwable t) {

            }
        } );


    }

    private void sendingCommenttoserverPicture(final PictureStatus holder, final String text, Integer postId, final Integer memberId) {
        Log.d( "ASdasdassdChecking", String.valueOf( memberId ) );
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        Api api = retrofit.create( Api.class );

        AddComment addComment = new AddComment( postId, memberId, text );

        Call<AddComment> addCommentCall = api.addingComment( addComment );
        addCommentCall.enqueue( new Callback<AddComment>() {
            @Override
            public void onResponse(Call<AddComment> call, Response<AddComment> response) {
                holder.commentcontent.setText( text );

                gettingMemberObjectPicture( holder, memberId );


            }


            @Override
            public void onFailure(Call<AddComment> call, Throwable t) {

            }
        } );


    }

    private void sendingCommenttoserverText(final TextStatus holder, final String text, Integer postId, final Integer memberId) {
        Log.d( "ASdasdassdChecking", String.valueOf( memberId ) );
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        Api api = retrofit.create( Api.class );

        AddComment addComment = new AddComment( postId, memberId, text );

        Call<AddComment> addCommentCall = api.addingComment( addComment );
        addCommentCall.enqueue( new Callback<AddComment>() {
            @Override
            public void onResponse(Call<AddComment> call, Response<AddComment> response) {
                holder.commentcontent.setText( text );

                gettingMemberObjectText( holder, memberId );

                Toast.makeText( context, "Comment Posted Sucessfully!", Toast.LENGTH_SHORT ).show();

            }


            @Override
            public void onFailure(Call<AddComment> call, Throwable t) {

            }
        } );


    }

    private void gettingMemberObjectPicture(final PictureStatus holder, Integer memberId) {
        Log.d( "GETTINHERE", "WKWKWKWKWWK" );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        Api api = retrofit.create( Api.class );

        Call<MemberObject> memberObjectCall = api.getmemberbyid( memberId );

        memberObjectCall.enqueue( new Callback<MemberObject>() {
            @Override
            public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {
                Log.d( "ALALALALALALAL!!!@@@##", String.valueOf( response ) );
                Glide.with( context ).load( response.body().getData().getProfilePicture() ).into( holder.commentedby );
                holder.commentedbyname.setText( response.body().getData().getUsername() );
                holder.comments.setVisibility( View.INVISIBLE );
                Toast.makeText( context, "Comment Posted Sucessfully!", Toast.LENGTH_SHORT ).show();


                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<MemberObject> call, Throwable t) {

            }
        } );

    }

    private void displayingnumoflikes(final Integer PostId) {
        friends = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        Api api = retrofit.create( Api.class );

        dialog = new Dialog( context );
        builder = new AlertDialog.Builder( context );
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
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

                LinearLayoutManager dpm = new LinearLayoutManager( context, LinearLayoutManager.VERTICAL, false );
                RecyclerView.Adapter freindsrecycleradapter = new LikeFriendAdapter( friends, context, PostId );
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

    private void likingPost(Integer memberId, Integer postId, final Integer pos) {

//setting Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        Api api = retrofit.create( Api.class );

        Call<LikeUnLike> liking = api.like( memberId, postId );
        liking.enqueue( new Callback<LikeUnLike>() {
            @Override
            public void onResponse(Call<LikeUnLike> call, Response<LikeUnLike> response) {
          try{
              Toast.makeText( context, "Post liked", Toast.LENGTH_SHORT ).show();
              arrayoflikes.set( pos,response.body().getData() );

          }
catch (Exception e){

}
            }

            @Override
            public void onFailure(Call<LikeUnLike> call, Throwable t) {

            }
        } );


    }

    private void unlikingPost(Integer memberId, Integer postId, final Integer pos) {

//setting Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        Api api = retrofit.create( Api.class );

        Call<LikeUnLike> liking = api.unlikePost( memberId, postId );
        liking.enqueue( new Callback<LikeUnLike>() {
            @Override
            public void onResponse(Call<LikeUnLike> call, Response<LikeUnLike> response) {
              try{
                  Toast.makeText( context, "Post unliked", Toast.LENGTH_SHORT ).show();
                  arrayoflikes.set( pos,response.body().getData() );

              }catch (Exception e){

              }
            }

            @Override
            public void onFailure(Call<LikeUnLike> call, Throwable t) {

            }
        } );


    }

    private void gettingMemberObjectText(final TextStatus holder, Integer memberId) {
        Log.d( "GETTINHERE", "WKWKWKWKWWK" );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Api.Base_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        Api api = retrofit.create( Api.class );

        Call<MemberObject> memberObjectCall = api.getmemberbyid( memberId );

        memberObjectCall.enqueue( new Callback<MemberObject>() {
            @Override
            public void onResponse(Call<MemberObject> call, Response<MemberObject> response) {
                Log.d( "ALALALALALALAL!!!@@@##", String.valueOf( response ) );
                Glide.with( context ).load( response.body().getData().getProfilePicture() ).into( holder.commentedby );
                holder.commentedbyname.setText( response.body().getData().getUsername() );
                Toast.makeText( context, "Comment Posted Sucessfully!", Toast.LENGTH_SHORT ).show();

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<MemberObject> call, Throwable t) {

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


        dialog = new Dialog( context );
        builder = new AlertDialog.Builder( context );
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View customView = inflater.inflate( R.layout.postalllikes, null );
        builder.setTitle( "Comments are" );

        final RecyclerView recyclerview = (RecyclerView) customView.findViewById( R.id.recyclerview );

        final Call<AllCommentsOuterResponse> allCommentsOuterResponseCall = api.commentsall( postId );
        allCommentsOuterResponseCall.enqueue( new Callback<AllCommentsOuterResponse>() {
            @Override
            public void onResponse(Call<AllCommentsOuterResponse> call, Response<AllCommentsOuterResponse> response) {

                List<InnerArrayAllComments> allCommentsOuterResponses = response.body().getData();

                Log.d( "CheckingOut", String.valueOf( allCommentsOuterResponses.size() ) );
                Log.d( "CheckingOut", String.valueOf( allCommentsOuterResponses.size() ) );
                for (int i = 0; i < allCommentsOuterResponses.size(); i++) {
                    comments.add( new CommentHistory( response.body().getData().get( i ).getMember().getUsername(),
                            response.body().getData().get( i ).getMember().getProfilePicture(),
                            response.body().getData().get( i ).getContent() ) );


                }

                LinearLayoutManager dpm = new LinearLayoutManager( context, LinearLayoutManager.VERTICAL, false );
                RecyclerView.Adapter freindsrecycleradapter = new CommentsAdapter( comments, context );
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

    private void fbsharing(final String imageurl) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {

                    Log.d( "IMAGEURL", imageurl );
                    url = new URL( imageurl );
                    image = BitmapFactory.decodeStream( url.openConnection().getInputStream() );
                    fbdialog.dismiss();

                    SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap( image ).build();
                    SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                            .addPhoto( sharePhoto ).build();
                    if (ShareDialog.canShow( SharePhotoContent.class )) {

                        shareDialog = new ShareDialog( (Activity) context );

                        shareDialog.show( sharePhotoContent, ShareDialog.Mode.AUTOMATIC );

                    }
                    Log.d( "IMAGEURL", String.valueOf( image ) );

                } catch (Exception e) {


                    e.printStackTrace();
                }

            }
        };

        Thread thread = new Thread( runnable );
        thread.start();

    }

    private void sharingplatform(final String imageurl) {
        dialog = new Dialog( context );
        builder = new AlertDialog.Builder( context );
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View customView = inflater.inflate( R.layout.sharing_platforms, null );
        Button fb = (Button) customView.findViewById( R.id.fb );
        Button insta = (Button) customView.findViewById( R.id.insta );
        builder.setTitle( "Select your platform" );

        fb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                fbdialog = new ProgressDialog( context );
                fbdialog.setCancelable( false );
                fbdialog.setMessage( "Processing.." );
                fbdialog.show();


                fbsharing( imageurl );
            }
        } );

        insta.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                instadialog = new ProgressDialog( context );
                instadialog.setCancelable( false );
                instadialog.setMessage( "Processing.." );
                instadialog.show();

                instasharing( imageurl );
            }
        } );


        builder.setView( customView );
        dialog = builder.create();
        dialog.show();

    }

    // Static inner class to initialize the views of rows
    static class PictureStatus extends RecyclerView.ViewHolder {

        CircleImageView userdp, commentedby;
        TextView name, textofthepost, date, time, comments,numoflikes, commentedbyname, commentcontent;
        ImageView image;
        Button like, comment, share, unlike;

        public PictureStatus(View itemView) {
            super( itemView );

            userdp = (CircleImageView) itemView.findViewById( R.id.userimage );
            commentedby = (CircleImageView) itemView.findViewById( R.id.commentedbyimage );
            name = (TextView) itemView.findViewById( R.id.name );
            commentedbyname = (TextView) itemView.findViewById( R.id.commentedbyname );
            commentcontent = (TextView) itemView.findViewById( R.id.commentedcontent );
            textofthepost = (TextView) itemView.findViewById( R.id.textofpost );
            image = (ImageView) itemView.findViewById( R.id.picofthepost );
            like = (Button) itemView.findViewById( R.id.like );
            unlike = (Button) itemView.findViewById( R.id.unlike );
            comment = (Button) itemView.findViewById( R.id.comment );
            share = (Button) itemView.findViewById( R.id.share );
            date = (TextView) itemView.findViewById( R.id.date );
            time = (TextView) itemView.findViewById( R.id.time );
            comments= (TextView) itemView.findViewById( R.id.comments );
            numoflikes = (TextView) itemView.findViewById( R.id.numoflikes );


        }

    }

    static class TextStatus extends RecyclerView.ViewHolder {
        CircleImageView userdp, commentedby;
        TextView name, textofthepost, date, time, numoflikes,comments, commentedbyname, commentcontent;
        Button like, comment, share, unlike;

        public TextStatus(View itemView) {
            super( itemView );
            userdp = (CircleImageView) itemView.findViewById( R.id.userimage );
            commentedby = (CircleImageView) itemView.findViewById( R.id.commentedbyimage );
            name = (TextView) itemView.findViewById( R.id.name );
            commentedbyname = (TextView) itemView.findViewById( R.id.commentedbyname );
            commentcontent = (TextView) itemView.findViewById( R.id.commentedcontent );
            textofthepost = (TextView) itemView.findViewById( R.id.textofpost );
            like = (Button) itemView.findViewById( R.id.like );
            unlike = (Button) itemView.findViewById( R.id.unlike );
            comment = (Button) itemView.findViewById( R.id.comment );
            share = (Button) itemView.findViewById( R.id.share );
            date = (TextView) itemView.findViewById( R.id.date );
            time = (TextView) itemView.findViewById( R.id.time );
            numoflikes = (TextView) itemView.findViewById( R.id.numoflikes );
            comments= (TextView) itemView.findViewById( R.id.comments );

            share.setVisibility( View.INVISIBLE );

        }


    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
