package com.api.challenge.common;

import com.api.challenge.exceptions.ApiError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseApiDTO<T> {
    private T data;
    private ApiError error;
}
