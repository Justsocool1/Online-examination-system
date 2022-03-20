package com.lins.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperManager {
    private String paperId;
    private String questionType;
    private String questionId;
}
