package br.com.higo.apiescola.dto;

import lombok.Data;

@Data
public class ErroResponseDTO {

    private String message;

    public ErroResponseDTO(String message) {
        this.message = message;
    }
}
