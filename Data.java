import android.nfc.Tag;
import android.provider.ContactsContract;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class Data {
    String clothname;
    String clothsize;
    String buyurl;
    String clothmemo;
    ArrayList<String> clothtag;
    ArrayList<String> clothcategory;
    //clothphoto;
    //dailyphoto;

    public data(){}

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
    public ArrayList<> getClothPhoto(){
        //return clothPhoto;
    }
    public ArrayList<> getDailyPhoto(){
        //return clothPhoto;
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
        this.photo = photo;
    }

    //adddata
    public void addDailyPhoto(Photo photo){
        this.photo = photo;
    }
    public void addCategory(Category category){
        this.category = category;
    }
    public void addClothtag(Tag tag){
        this.tag = tag;
    }

    //deletedata

}
