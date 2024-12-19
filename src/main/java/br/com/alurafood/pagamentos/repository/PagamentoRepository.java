package br.com.alurafood.pagamentos.repository;

import br.com.alurafood.pagamentos.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PagamentoRepository extends PagingAndSortingRepository<Pagamento, Long>, JpaRepository<Pagamento, Long> {
}
