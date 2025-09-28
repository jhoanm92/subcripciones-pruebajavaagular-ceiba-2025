package com.api.btg.mapper;

import com.api.btg.dto.SuscripcionDto;
import com.api.btg.entity.Suscripcion;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface SuscripcionDtoMapper {

    SuscripcionDto toDto(Suscripcion suscripcion);

    @Mapping(target = "id.fondoId", source = "fondo.id")
    @Mapping(target = "id.clienteId", source = "cliente.id")
    Suscripcion toEntity(SuscripcionDto suscripcionDto);
}
