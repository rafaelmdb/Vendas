package com.github.rafaelmdb;

import com.github.rafaelmdb.domain.dto.ProdutoDTO;
import com.github.rafaelmdb.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class VendasApplication {
    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }

    @Autowired
    ProdutoService pessoaService;

    @Autowired
    ProdutoService produtoService;

    //@Bean
    public CommandLineRunner init() {
        return args -> {
            List<ProdutoDTO> produtos = new ArrayList<ProdutoDTO>();
            produtos.add(new ProdutoDTO("1", "1", "Produto 1", "Disponível"));
            produtos.add(new ProdutoDTO());
            //produtoService.criar(produtos);
        };
    }
}

