package com.lifeline.fyp.fyp.socialMedia;

import com.lifeline.fyp.fyp.models.Member;

/**
 * Created by Mujtaba_khalid on 4/27/2018.
 */

public class NotificationStructure {

    private Integer notificationid;
    private String content;
    private String time;
    private Integer postId;

    private String userame;
    private String email;
    private String piclink;
    private String notificationtype;
    private boolean isSeen;

    public NotificationStructure(Integer notificationid,String content, String time, Integer postId, String userame, String email, String piclink,String notificationtype, boolean isSeen) {
        this.notificationid = notificationid;
        this.content = content;
        this.time = time;
        this.postId = postId;
        this.userame = userame;
        this.email = email;
        this.piclink = piclink;
        this.notificationtype = notificationtype;
        this.isSeen = isSeen;
    }

    public NotificationStructure(Integer notificationid,String content, String time, String userame, String email, String piclink,String notificationtype,boolean isSeen) {
        this.content = content;
        this.time = time;
        this.userame = userame;
        this.email = email;
        this.piclink = piclink;
        this.notificationtype = notificationtype;
        this.isSeen = isSeen;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public Integer getPostId() {
        return postId;
    }

    public String getUserame() {
        return userame;
    }

    public String getEmail() {
        return email;
    }

    public String getPiclink() {
        return piclink;
    }


    public String getNotificationtype() {
        return notificationtype;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public Integer getNotificationid() {
        return notificationid;
    }
}
