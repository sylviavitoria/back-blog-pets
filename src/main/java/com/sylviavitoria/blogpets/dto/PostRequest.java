package com.sylviavitoria.blogpets.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    private Long id;
    
    @NotBlank(message = "O título é obrigatório")
    @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres")
    private String titulo;
    
    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 10, message = "A descrição deve ter pelo menos 10 caracteres")
    private String descricao;
    
    @NotBlank(message = "O autor é obrigatório")
    @Size(min = 3, message = "O nome do autor deve ter pelo menos 3 caracteres")
    private String autor;
}