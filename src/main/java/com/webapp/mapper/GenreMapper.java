package com.webapp.mapper;

import com.webapp.dto.GenreDto;
import com.webapp.model.GenreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface GenreMapper {

    GenreMapper MAPPER = Mappers.getMapper(GenreMapper.class);

    GenreDto toDTO(GenreEntity genre);
}
