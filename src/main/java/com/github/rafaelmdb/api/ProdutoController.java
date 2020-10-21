package com.github.rafaelmdb.api;

import com.github.rafaelmdb.domain.dto.ProdutoDTO;
import com.github.rafaelmdb.domain.entity.Produto;
import com.github.rafaelmdb.domain.repo.ProdutoRepo;
import com.github.rafaelmdb.domain.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService){
        this.produtoService = produtoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID criar(@RequestBody ProdutoDTO dto){
        return produtoService.criar(Arrays.asList(dto)).get(0).getId();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UUID alterar(@PathVariable UUID id, @RequestBody ProdutoDTO dto){
        return produtoService.alterar(id, dto).getId();
    }
}
