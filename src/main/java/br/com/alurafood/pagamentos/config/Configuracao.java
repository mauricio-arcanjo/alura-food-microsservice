package br.com.alurafood.pagamentos.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;

@Configuration
public class Configuracao {

    //torna possivel usar classe ModelMapper e habilita o @autowired usado na classe PagamentoService
    @Bean
    public ModelMapper obterModelMapper() {
        return new ModelMapper();
    }

}
