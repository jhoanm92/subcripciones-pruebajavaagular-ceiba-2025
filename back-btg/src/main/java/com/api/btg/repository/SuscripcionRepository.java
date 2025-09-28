package com.api.btg.repository;

import com.api.btg.entity.Suscripcion;
import com.api.btg.entity.SuscripcionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, SuscripcionId> {

    @Query("select s from Suscripcion s where s.id.clienteId = :clienteId and s.id.fondoId = :fondoId")
    Optional<Suscripcion> findByIdClienteIdAndIdFondoId(@Param("clienteId") Integer clienteId,
                                                        @Param("fondoId") Integer fondoId);

    @Query("select s from Suscripcion s where s.cliente.id = :id")
    List<Suscripcion> findByClienteId(@Param("id") Integer id);


}