package org.zust.account.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.dto.BookDto;
import org.zust.interfaceapi.dto.BookUserDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="throw_records")
@NoArgsConstructor
public class ThrowRecordsEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="type")
    private String type;
    @Column(name="datetime")
    private Date datetime;
    @Column(name="cost")
    private Double cost;
    @Column(name="book")
    private Integer book;
    @Column(name="advertisement")
    private Integer advertisement;
    @Column(name="owner")
    private Integer owner;

    public ThrowRecordsEntity(String type, Date datetime, Double cost, Integer book, Integer advertisement, Integer owner) {
        this.type = type;
        this.datetime = datetime;
        this.cost = cost;
        this.book = book;
        this.advertisement = advertisement;
        this.owner = owner;
    }
}
