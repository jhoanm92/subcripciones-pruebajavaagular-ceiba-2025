package com.api.btg.mapper;

import com.api.btg.dto.ClienteDto;
import com.api.btg.entity.Cliente;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ClienteDtoMapper {

    ClienteDto toDto(Cliente cliente);
    Cliente toEntity(ClienteDto cliente);
}
