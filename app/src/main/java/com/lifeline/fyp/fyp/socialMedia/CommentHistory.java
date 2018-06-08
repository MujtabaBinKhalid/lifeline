package com.lifeline.fyp.fyp.socialMedia;

/**
 * Created by Mujtaba_khalid on 4/25/2018.
 */

public class CommentHistory {

    private String name;
    private String picture;
    private String content;

    public CommentHistory(String name, String picture, String content) {
        this.name = name;
        this.picture = picture;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getContent() {
        return content;
    }
}
