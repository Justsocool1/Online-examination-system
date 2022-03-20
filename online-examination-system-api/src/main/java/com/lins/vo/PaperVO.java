package com.lins.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaperVO  {
    private String subject;
    private String question;
    private Integer score;
    private String type;
    private String section;
    private Integer level;
}
