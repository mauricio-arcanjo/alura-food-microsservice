package br.com.alurafood.pagamentos.dto;

import br.com.alurafood.pagamentos.model.Status;

import java.math.BigDecimal;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class PagamentoDto {
//
//    private Long id;
//    private BigDecimal valor;
//    private String nome;
//    private String numero;
//    private String expiracao;
//    private String codigo;
//    private Status status;
//    private Long formaDePagamentoId;
//    private Long pedidoId;
//
//}

public record PagamentoDtoRec(
         Long id,
         BigDecimal valor,
         String nome,
         String numero,
         String expiracao,
         String codigo,
         Status status,
         Long formaDePagamentoId,
         Long pedidoId
){

}
