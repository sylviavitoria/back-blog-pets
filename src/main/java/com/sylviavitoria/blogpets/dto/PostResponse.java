package com.sylviavitoria.blogpets.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PostResponse {

    String titulo;
    String descricao;
    String autor;

}
