package insta.get.likes.instagram.followers.util;

public class MyCommodity implements Commodity{
    private String id;

    private String payKey;

    private String packName;

    public MyCommodity(String id, String payKey, String packName){
        this.id =id;
        this.payKey =payKey;
        this.packName =packName;
    }

    @Override
    public String getId() {
      return id;
    }

    @Override
    public String getPayKey() {
      return payKey;
    }

    @Override
    public String getPackName() {
      return packName;
    }
}
