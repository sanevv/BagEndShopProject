package com.github.mymvcspring.repository;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tbl_main_page")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgno;

    private String imgname;

    private String imgfilename;
}
