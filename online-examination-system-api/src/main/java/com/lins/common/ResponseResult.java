package com.lins.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseResult<T> {
    private Integer code;
    private boolean success = false;
    private String message;
    private T data;


}
