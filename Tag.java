public class Tag {
    private String tagcolor;
    private String tagname;

    public Tag(){}

    public Tag(String tagcolor, String tagname){
        this.tagcolor = tagcolor;
        this.tagname = tagname;
    }

    public String getTagcolor(){
        return tagcolor;
    }

    public String getTagname(){
        return tagname;
    }

    public void setTagcolor(String color){
        this.tagcolor = color;
    }

    public void setTagname(String name){
        this.tagname = name;
    }
}
