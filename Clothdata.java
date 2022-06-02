import android.nfc.Tag;
import android.provider.ContactsContract;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class Clothdata {
    private String clothname;
    private String clothsize;
    private String buyurl;
    private String clothmemo;
    private ArrayList<Tag> clothtag;
    private ArrayList<Category> clothcategory;
    private ArrayList<Photo> clothphoto;
    private ArrayList<Photo> dailyphoto;

    public Clothdata(){}

    public Clothdata(String name, String size, String url, String memo, Tag tag, Category category, Photo cloth, Photo daily){
        this.clothname = name;
        this.clothsize = size;
        this.buyurl = url;
        this.clothmemo = memo;
        this.clothtag = tag;
        this.clothphoto = cloth;
        this.dailyphoto = daily;
    }

    //getdata
    public String getClothname(){
        return clothname;
    }
    public String getClothsize(){
        return clothsize;
    }
    public String getClothmemo(){
        return clothmemo;
    }
    public String getBuyurl(){
        return buyurl;
    }
    public ArrayList<Tag> getClothTag(){
        return clothtag;
    }
    public ArrayList<String> getClothcategory(){
        return clothcategory;
    }
    public ArrayList<Photo> getClothPhoto(){
        return clothphoto;
    }
    public ArrayList<Photo> getDailyPhoto(){
        return clothphoto;
    }

    //setdata
    public void setClothname(String clothname){
        this.clothname = clothname;
    }
    public void setClothsize(String clothsize){
        this.clothsize = clothsize;
    }
    public void setClothmemo(String clothmemo){
        this.clothmemo = clothmemo;
    }
    public void setBuyurl(String buyurl){
        this.buyurl = buyurl;
    }
    public void setClothphoto(Photo photo){
        this.clothphoto = photo;
    }

    //adddata
    public void addDailyPhoto(Photo photo){
        this.dailyphoto = photo;
    }
    public void addCategory(Category category){
        this.clothcategory = category;
    }
    public void addClothtag(Tag tag){
        this.clothtag = tag;
    }

    //deletedata
    public void deleteDailyphoto(int index){}
    public void deleteClothcategory(String tag){}
}
