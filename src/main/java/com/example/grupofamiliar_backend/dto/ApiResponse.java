package com.example.grupofamiliar_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;

    private String mensaje;

    private T data;

    private String error;

    public ApiResponse(boolean success, String mensaje) {
        this.success = success;
        this.mensaje = mensaje;
    }

    public ApiResponse(boolean success, String mensaje, T data) {
        this.success = success;
        this.mensaje = mensaje;
        this.data = data;
    }

}
