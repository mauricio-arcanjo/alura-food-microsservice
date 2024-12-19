package br.com.alurafood.pagamentos.mapper;

import br.com.alurafood.pagamentos.dto.PagamentoDtoRec;
import br.com.alurafood.pagamentos.model.Pagamento;

public class PagamentoMapper {

    public static Pagamento mapToPagamento(PagamentoDtoRec pagamentoDto){

        return new Pagamento(
                pagamentoDto.id(),
                pagamentoDto.valor(),
                pagamentoDto.nome(),
                pagamentoDto.numero(),
                pagamentoDto.expiracao(),
                pagamentoDto.codigo(),
                pagamentoDto.status(),
                pagamentoDto.pedidoId(),
                pagamentoDto.formaDePagamentoId()
        );
    }

    public static PagamentoDtoRec mapToPagamentoDto(Pagamento pagamento){

        return new PagamentoDtoRec(
                pagamento.getId(),
                pagamento.getValor(),
                pagamento.getNome(),
                pagamento.getNumero(),
                pagamento.getExpiracao(),
                pagamento.getCodigo(),
                pagamento.getStatus(),
                pagamento.getFormaDePagamentoId(),
                pagamento.getPedidoId()
        );

    }

}
