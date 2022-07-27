package com.ivanm.myfinances.service;

import java.util.List;

import com.ivanm.myfinances.model.entity.Lancamento;
import com.ivanm.myfinances.model.enums.StatusLancamento;

public interface LancamentoService {
  Lancamento salvar(Lancamento lancamento);

  Lancamento atualizar(Lancamento lancamento);

  void deletar(Lancamento lancamento);

  List<Lancamento> buscar(Lancamento lancamentoFiltro);

  void atualizarStatus(Lancamento lancamento, StatusLancamento status);

  void validar(Lancamento lancamento);
}
