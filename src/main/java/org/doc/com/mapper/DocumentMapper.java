package org.doc.com.mapper;

import org.doc.com.dto.DocumentCreateDto;
import org.doc.com.dto.DocumentDto;
import org.doc.com.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "user.id", target = "userId")
    DocumentDto toDto(Document document);

    @Mapping(source = "userId", target = "user.id")
    Document toEntity(DocumentDto documentDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Document documentCreateDtoToEntity(DocumentCreateDto documentCreateDto);
}