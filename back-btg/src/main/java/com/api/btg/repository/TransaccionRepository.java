package com.api.btg.repository;

import com.api.btg.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {

    @Query("select t from Transaccion t where t.cliente.id = :id")
    List<Transaccion> findByClienteId(@Param("id") Integer id);


}