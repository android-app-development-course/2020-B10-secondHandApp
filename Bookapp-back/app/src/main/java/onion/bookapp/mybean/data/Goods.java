package onion.bookapp.mybean.data;

public class Goods {
    private String goodsid;
    private String publisherid;
    private String images;
    private String publishTime;
    private String title;
    private String detail;
    private String sort;
    private String price;

    public Goods(){}


    public Goods(String goodsid, String publisherid, String images, String publishTime, String title, String detail, String sort, String price) {
        this.goodsid = goodsid;
        this.publisherid = publisherid;
        this.images = images;
        this.publishTime = publishTime;
        this.title = title;
        this.detail = detail;
        this.sort = sort;
        this.price = price;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getPublisherid() {
        return publisherid;
    }

    public void setPublisherid(String publisherid) {
        this.publisherid = publisherid;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
