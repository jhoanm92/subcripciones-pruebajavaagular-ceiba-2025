package com.api.btg.repository;

import com.api.btg.entity.EstadoTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoTransaccionRepository extends JpaRepository<EstadoTransaccion, Integer> {
}