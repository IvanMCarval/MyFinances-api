package com.ivanm.myfinances.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivanm.myfinances.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
  
}
