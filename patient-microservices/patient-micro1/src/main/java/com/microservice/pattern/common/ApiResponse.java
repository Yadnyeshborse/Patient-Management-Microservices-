package com.microservice.pattern.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {

    private String status;
    private String message;
    private T data;
    private Integer total;
    private Integer code;

    public ApiResponse(String status, String message, T data, Integer total, Integer code) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.total = total;
        this.code = code;
    }

    public static <T> ApiResponse<T> success(String message, T data, Integer total) {
        return new ApiResponse<>("success", message, data, total, 200);
    }

    public static <T> ApiResponse<T> error(String message, int code) {
        return new ApiResponse<>("error", message, null, 0, code);
    }

    // getters & setters
}