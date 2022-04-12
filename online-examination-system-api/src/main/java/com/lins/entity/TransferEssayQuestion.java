package com.lins.entity;

import lombok.Data;

@Data
public class TransferEssayQuestion {
    private String teqId;
    private String questionId;
    private String stuAnswer;
    private Integer score;
}
