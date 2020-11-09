package com.github.rafaelmdb.api;

import com.github.rafaelmdb.domain.entity.Produto;
import com.github.rafaelmdb.domain.repo.ProdutoRepo;
import com.github.rafaelmdb.dto.ProdutoDTO;
import com.github.rafaelmdb.domain.service.ProdutoService;
import com.github.rafaelmdb.dto.converters.ProdutoConverter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/produto")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoConverter produtoConverter;
    private final ProdutoRepo produtoRepo;

    public ProdutoController(ProdutoService produtoService, ProdutoConverter produtoConverter, ProdutoRepo produtoRepo) {
        this.produtoService = produtoService;
        this.produtoConverter = produtoConverter;
        this.produtoRepo = produtoRepo;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID criar(@RequestBody ProdutoDTO dto) {
        Produto produto = produtoConverter.createFrom(dto);
        return produtoService.criar(produto).getId();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UUID alterar(@PathVariable UUID id, @RequestBody ProdutoDTO dto) {
        Produto produto = produtoConverter.createFrom(dto);
        return produtoService.alterar(id, produto).getId();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        produtoService.remover(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoDTO obterPorId(@PathVariable UUID id) {
        ProdutoDTO dto = produtoConverter
                .createFrom(
                        produtoService.obterPorId(id));
        return dto;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoDTO> obterPorExemplo(ProdutoDTO filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING);

        Example example = Example.of(produtoConverter.createFrom(filtro));

        List<ProdutoDTO> resultado = produtoConverter.createFromEntities(
                produtoRepo.findAll(example));

        return resultado;
    }
}