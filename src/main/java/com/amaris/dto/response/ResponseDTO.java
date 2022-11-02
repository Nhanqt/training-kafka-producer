package com.amaris.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDTO<T> {
    private int statusCode;
    private String message;
    private T data;
}
