package ru;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ta")
public class TA implements Serializable{
    @Id
    @Column(name = "wa", nullable = false)
    public String wa;

    @Column(name = "wr", nullable = false)
    public String wr;

    @Lob
    @Column(name = "b_uk", nullable = false)
    public byte[] b_uk;

}
