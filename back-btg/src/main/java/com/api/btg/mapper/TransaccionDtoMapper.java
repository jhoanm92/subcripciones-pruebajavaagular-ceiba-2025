package com.api.btg.mapper;

import com.api.btg.dto.TransaccionDto;
import com.api.btg.entity.Transaccion;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface TransaccionDtoMapper {

    TransaccionDto toDto(Transaccion transaccion);
    Transaccion toEntity(TransaccionDto transaccionDto);
}
