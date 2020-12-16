package com.github.rafaelmdb.api;

import com.github.rafaelmdb.domain.entity.TabelaPreco;
import com.github.rafaelmdb.domain.entity.TabelaPrecoItem;
import com.github.rafaelmdb.domain.repo.TabelaPrecoRepo;
import com.github.rafaelmdb.domain.service.TabelaPrecoService;
import com.github.rafaelmdb.dto.TabelaPrecoDTO;
import com.github.rafaelmdb.dto.TabelaPrecoItemDTO;
import com.github.rafaelmdb.dto.converters.TabelaPrecoConverter;
import com.github.rafaelmdb.dto.converters.TabelaPrecoItemConverter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/tabelapreco")
public class TabelaPrecoController {

    private final TabelaPrecoService tabelaPrecoService;
    private final TabelaPrecoConverter tabelaPrecoConverter;
    private final TabelaPrecoItemConverter tabelaPrecoItemConverter;
    private final TabelaPrecoRepo tabelaPrecoRepo;

    public TabelaPrecoController(TabelaPrecoService tabelaPrecoService, TabelaPrecoConverter tabelaPrecoConverter, TabelaPrecoRepo tabelaPrecoRepo, TabelaPrecoItemConverter tabelaPrecoItemConverter) {
        this.tabelaPrecoService = tabelaPrecoService;
        this.tabelaPrecoConverter = tabelaPrecoConverter;
        this.tabelaPrecoRepo = tabelaPrecoRepo;
        this.tabelaPrecoItemConverter=tabelaPrecoItemConverter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID criar(@RequestBody TabelaPrecoDTO dto) {
        TabelaPreco tabelaPreco = tabelaPrecoConverter.createFrom(dto);
        return tabelaPrecoService.criar(tabelaPreco).getId();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UUID alterar(@PathVariable UUID id, @RequestBody TabelaPrecoDTO dto) {
        TabelaPreco tabelaPreco = tabelaPrecoConverter.createFrom(dto);
        return tabelaPrecoService.alterar(tabelaPreco).getId();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        tabelaPrecoService.remover(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public TabelaPrecoDTO obterPorId(@PathVariable UUID id) {
        TabelaPrecoDTO dto = tabelaPrecoConverter
                .createFrom(
                        tabelaPrecoService.obterPorId(id));
        return dto;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<TabelaPrecoDTO> obterPorExemplo(TabelaPrecoDTO filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING);

        Example example = Example.of(tabelaPrecoConverter.createFrom(filtro), matcher);

        List<TabelaPrecoDTO> resultado = tabelaPrecoConverter.createFromEntities(
                tabelaPrecoRepo.findAll(example));

        return resultado;
    }

    @PostMapping("{id}/itens")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID criar(@RequestBody TabelaPrecoItemDTO dto) {
        TabelaPrecoItem tabelaPrecoItem = tabelaPrecoItemConverter.createFrom(dto);
        return tabelaPrecoService.adicionarItem(tabelaPrecoItem).getId();
    }
}