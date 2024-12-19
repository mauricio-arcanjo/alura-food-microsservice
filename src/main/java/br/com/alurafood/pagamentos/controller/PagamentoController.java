package br.com.alurafood.pagamentos.controller;

import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.dto.PagamentoDtoRec;
import br.com.alurafood.pagamentos.service.PagamentoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

        @GetMapping ("/all/{pageNumber}")
        public List<PagamentoDto> listar(@PathVariable int pageNumber){

            PageRequest pageable = PageRequest.of(pageNumber, 10);
            return pagamentoService.obterTodos(pageable);

        }

        @GetMapping("/{id}")
        public ResponseEntity<PagamentoDto> obterPagamento(@PathVariable Long id){

            return ResponseEntity.ok(pagamentoService.obterPagamentoPorId(id));

        }

        @PostMapping
        public ResponseEntity<PagamentoDto> cadastrarPagemento(@RequestBody @Valid PagamentoDto pagamentoDto,
                                                                  UriComponentsBuilder uriComponentsBuilder){
            PagamentoDto pagamento = pagamentoService.criarPagamento(pagamentoDto);
            URI endereco = uriComponentsBuilder.path("/pagamentos/{id}")
                    .buildAndExpand(pagamento.getId())
                    .toUri();
            return ResponseEntity.created(endereco).body(pagamento);

//              return ResponseEntity.ok(pagamento);
        }

        @PutMapping("/{id}")
        public ResponseEntity<PagamentoDto> atualizarPagemento(@PathVariable @Valid Long id,
                                                                  @RequestBody @Valid PagamentoDto pagamentoDto){

            PagamentoDto pagamento = pagamentoService.atualizarPagamento(id, pagamentoDto);

            return ResponseEntity.ok(pagamento);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<PagamentoDto> remover(@PathVariable @NotNull Long id){

            pagamentoService.excluirPagamento(id);

            return ResponseEntity.noContent().build();
        }

        @PatchMapping("/{id}/confirmar")
        @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "pagamentoAutorizadoComIntegracaoPendente")
        public void confirmarPagamento(@PathVariable @NotNull Long id){
            pagamentoService.confirmarPagamento(id);
        }

//        Precisa ter a mesma assinatura do método do circuit breaker confirmarPagamento.
        public void pagamentoAutorizadoComIntegracaoPendente(Long id, Exception e){
            pagamentoService.alterarStatus(id);
        }
 
}
