package com.kognitive.offers.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private Long id ;

    @Column(name = "name")
    @XmlElement
    @NotBlank
    private String name;

    @Column(name = "validFrom")
    @XmlElement
    @NotBlank
    private String validFrom;

    @Column(name = "validTo")
    @XmlElement
    @NotBlank
    private String validTo;

    @Column(name = "location")
    @XmlElement
    @NotBlank
    private String location;

    @OneToOne
    @JoinColumn(name = "images_id")
    private Images images;
}
