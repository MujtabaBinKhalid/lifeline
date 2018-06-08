package com.lifeline.fyp.fyp.socialMedia;

/**
 * Created by Mujtaba_khalid on 4/20/2018.
 */

public class FetchingNewsFeedData {
    public enum PostType {
        PICTURE, TEXT;
    }

    private Integer postId;
    private Integer memberId;
    private String  name;
    private String email;
    private String picture;
    private String content;
    private String picturelink;
    private String noofcomments;
    private String nooflikes;
    private boolean hasImage;
    private String time;
    private String commentcontent;
    private String commenttime;
    private String commentedby;
    private String commentbypicturelink;
    private PostType type;
    private boolean hasLiked;


    public FetchingNewsFeedData(Integer postId, Integer memberId,
                                String name,String email,String picture,
                                String content, String picturelink, String noofcomments,
                                String nooflikes, boolean hasImage, String time, String commentcontent,
                                String commenttime, String commentedby, String commentbypicturelink, PostType type,boolean hasLiked) {
        this.postId = postId;
        this.memberId = memberId;
        this.name = name;
        this.picture = picture;
        this.content = content;
        this.picturelink = picturelink;
        this.noofcomments = noofcomments;
        this.nooflikes = nooflikes;
        this.hasImage = hasImage;
        this.time = time;
        this.email = email;
        this.commentcontent = commentcontent;
        this.commenttime = commenttime;
        this.commentedby = commentedby;
        this.commentbypicturelink = commentbypicturelink;
        this.type = type;
        this.hasLiked = hasLiked;
    }


    public FetchingNewsFeedData(Integer postId, Integer memberId,  String name,String email,String picture,
                                String content, String noofcomments,
                                String nooflikes, boolean hasImage, String time, String commentcontent,
                                String commenttime, String commentedby, String commentbypicturelink, PostType type,boolean hasLiked) {
        this.postId = postId;
        this.memberId = memberId;
        this.name = name;
        this.picture = picture;
        this.content = content;
        this.email = email;
        this.noofcomments = noofcomments;
        this.nooflikes = nooflikes;
        this.hasImage = hasImage;
        this.time = time;
        this.commentcontent = commentcontent;
        this.commenttime = commenttime;
        this.commentedby = commentedby;
        this.commentbypicturelink = commentbypicturelink;
        this.type = type;
        this.hasLiked = hasLiked;
    }

    public FetchingNewsFeedData(Integer postId, Integer memberId,  String name,String email,String picture,
                                String content, String picturelink, String noofcomments,
                                String nooflikes, boolean hasImage, String time, PostType type,boolean hasLiked) {
        this.postId = postId;
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.content = content;
        this.picturelink = picturelink;
        this.noofcomments = noofcomments;
        this.nooflikes = nooflikes;
        this.hasImage = hasImage;
        this.time = time;
        this.type = type;
        this.hasLiked = hasLiked;
    }


    public FetchingNewsFeedData(Integer postId, Integer memberId,  String name,String email,String picture,
                                String content, String noofcomments,
                                String nooflikes, boolean hasImage, String time, PostType type,boolean hasLiked) {
        this.postId = postId;
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.content = content;
        this.noofcomments = noofcomments;
        this.nooflikes = nooflikes;
        this.hasImage = hasImage;
        this.time = time;
        this.type = type;
        this.hasLiked = hasLiked;
    }
    public Integer getPostId() {
        return postId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public String getContent() {
        return content;
    }

    public String getPicturelink() {
        return picturelink;
    }

    public String getNoofcomments() {
        return noofcomments;
    }

    public String getNooflikes() {
        return nooflikes;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public String getTime() {
        return time;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public String getCommenttime() {
        return commenttime;
    }

    public String getCommentedby() {
        return commentedby;
    }

    public String getCommentbypicturelink() {
        return commentbypicturelink;
    }

    public PostType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }

    public boolean isHasLiked() {
        return hasLiked;
    }


    public void setHasLiked(boolean hasLiked) {
        this.hasLiked = hasLiked;
    }
}
