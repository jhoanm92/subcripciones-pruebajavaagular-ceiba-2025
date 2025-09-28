package com.api.btg.mapper;

import com.api.btg.dto.EstadoTransaccionDto;
import com.api.btg.entity.EstadoTransaccion;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface EstadoTransaccionDtoMapper {

    EstadoTransaccionDto toDto(EstadoTransaccion estadoTransaccion);
}
