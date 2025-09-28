package com.api.btg.mapper;

import com.api.btg.dto.FondoDto;
import com.api.btg.entity.Fondo;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface FondoDtoMapper {

    FondoDto toDto(Fondo fondo);
}
