package com.github.rafaelmdb;

import com.github.rafaelmdb.dto.ProdutoDTO;
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

    //@Bean
    public CommandLineRunner init() {
        return args -> {
        };
    }
}

