package org.zust.interfaceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThrowRecordsDto {
    private Integer id;
    // 投放类型 "点击"或"查看"
    private String type;
    // 投放时间
    private Date datetime;
    // 单次花费
    private Double cost;
    // 投放到的书
    private BookDto book;
    // 投放的广告
    private AdvertisementDto advertisement;
    // 查看广告的读书用户
    private AdUserDto owner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public AdvertisementDto getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(AdvertisementDto advertisement) {
        this.advertisement = advertisement;
    }

    public AdUserDto getOwner() {
        return owner;
    }

    public void setOwner(AdUserDto owner) {
        this.owner = owner;
    }
}
