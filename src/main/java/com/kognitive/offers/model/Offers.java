package com.kognitive.offers.model;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@XmlRootElement
@Table(name = "offers")
public class Offers implements Serializable {

    @XmlElement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @XmlElement
    private String name;

    @Column(name = "validFrom")
    @XmlElement
    private String validFrom;

    @Column(name = "validTo")
    @XmlElement
    private String validTo;

    @Column(name = "location")
    @XmlElement
    private String location;

    @OneToOne
    @JoinColumn(name = "images_id")
    private Images images;
}
