package com.sylviavitoria.blogpets.mapper;

import com.sylviavitoria.blogpets.dto.PostRequest;
import com.sylviavitoria.blogpets.dto.PostResponse;
import com.sylviavitoria.blogpets.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {
    
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
    
    @Mapping(target = "dataCriacao", ignore = true)
    Post toEntity(PostRequest postRequest);
    
    PostResponse toResponse(Post post);
    
    @Mapping(target = "dataCriacao", ignore = true)
    void updateEntityFromRequest(PostRequest postRequest, @MappingTarget Post post);
}