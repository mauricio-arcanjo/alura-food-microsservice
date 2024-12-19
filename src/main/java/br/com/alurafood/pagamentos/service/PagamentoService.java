package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.dto.PagamentoDtoRec;
import br.com.alurafood.pagamentos.http.PedidoClient;
import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.model.Status;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PedidoClient pedido;

    public List<PagamentoDto> obterTodos(PageRequest paginacao){

        Iterable<Pagamento> pagamentos = pagamentoRepository.findAll(paginacao);

        return StreamSupport.stream(pagamentos.spliterator(), false)
                .map(p -> modelMapper.map(p, PagamentoDto.class))
//                .map(p -> PagamentoMapper.mapToPagamentoDto(p))
                .toList();

    }

    public PagamentoDto obterPagamentoPorId(Long id){

        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pagamento, PagamentoDto.class);
//        return PagamentoMapper.mapToPagamentoDto(pagamento);
    }

    public PagamentoDto criarPagamento(PagamentoDto pagamentoDto){

        Pagamento pagamento = modelMapper.map(pagamentoDto, Pagamento.class);
//        Pagamento pagamento = PagamentoMapper.mapToPagamento(pagamentoDto);
        pagamento.setStatus(Status.CRIADO);
        pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDto.class);
//        return PagamentoMapper.mapToPagamentoDto(pagamento);

    }

    public PagamentoDto atualizarPagamento(Long id, PagamentoDto pagamentoDto){

        Pagamento pagamento = modelMapper.map(pagamentoDto, Pagamento.class);
//        Pagamento pagamento = PagamentoMapper.mapToPagamento(pagamentoDto);
        pagamento.setId(id);
        pagamento = pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDto.class);
//        return PagamentoMapper.mapToPagamentoDto(pagamento);

    }

    public void excluirPagamento(Long id){

        pagamentoRepository.deleteById(id);

    }

    //Implementar método também no controller
    public void confirmarPagamento(Long id){

        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);

        if (!pagamento.isPresent()){
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO);
        pagamentoRepository.save(pagamento.get());
        pedido.atualizaPagamento(pagamento.get().getPedidoId());

    }


    public void alterarStatus(Long id) {

        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);

        if (!pagamento.isPresent()){
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        pagamentoRepository.save(pagamento.get());

    }
}
