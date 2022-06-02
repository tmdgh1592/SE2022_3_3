public class Photo {
    private String photoid;
    private String photoname;
    private int photoresid;

    public Photo(){}

    public Photo(String photoid, String photoname, int photoresid){
        this.photoid = photoid;
        this.photoname = photoname;
        this.photoresid = photoresid;
    }

    public String getPhotoid(){
        return photoid;
    }

    public String getPhotoname(){
        return photoname;
    }

    public int getPhotoresid(){
        return photoresid;
    }

    public void setPhotoid(String photoid){
        this.photoid = photoid;
    }

    public void setPhotoname(String photoname){
        this.photoname = photoname;
    }

    public void setPhotoresid(int photoresid){
        this.photoresid = photoresid;
    }
}
