package org.doc.com.mapper;

import org.doc.com.dto.DocumentCreateDto;
import org.doc.com.dto.DocumentCreateDtoWithToken;
import org.doc.com.dto.DocumentDto;
import org.doc.com.entity.Document;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    DocumentDto toDto(Document document);

    Document toEntity(DocumentDto documentDto);

    Document documentCreateDtoToEntity(DocumentCreateDto documentCreateDto);

    Document documentCreateWithToken(DocumentCreateDtoWithToken documentCreateDtoWithToken);
}