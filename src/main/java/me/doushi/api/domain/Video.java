package me.doushi.api.domain;

/**
 * Created by songlijun on 15/10/13.
 */
public class Video {

    private String id;
    private String title; //标题
    private String video;// video地址
    private String pic; // 图片地址
    private int type;//video类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", video='" + video + '\'' +
                ", pic='" + pic + '\'' +
                ", type=" + type +
                '}';
    }

    public Video() {
        super();
    }

    public Video(String id, String title, String video, String pic, int type) {
        this.id = id;
        this.title = title;
        this.video = video;
        this.pic = pic;
        this.type = type;
    }
}