package com.github.rafaelmdb.api;

import com.github.rafaelmdb.domain.entity.TabelaPrecoItem;
import com.github.rafaelmdb.domain.repo.TabelaPrecoItemRepo;
import com.github.rafaelmdb.domain.service.MessageService;
import com.github.rafaelmdb.domain.service.TabelaPrecoService;
import com.github.rafaelmdb.dto.TabelaPrecoDTO;
import com.github.rafaelmdb.dto.TabelaPrecoItemDTO;
import com.github.rafaelmdb.dto.converters.TabelaPrecoItemConverter;
import com.github.rafaelmdb.exception.RegraNegocioException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/tabelapreco/{tabelaPrecoId}/itens")
public class TabelaPrecoItemController {
    private final MessageService messageService;
    private final TabelaPrecoService tabelaPrecoService;
    private final TabelaPrecoItemConverter tabelaPrecoItemConverter;
    private final TabelaPrecoItemRepo tabelaPrecoItemRepo;

    public TabelaPrecoItemController(MessageService messageService, TabelaPrecoService tabelaPrecoService, TabelaPrecoItemConverter tabelaPrecoItemConverter, TabelaPrecoItemRepo tabelaPrecoItemRepo) {
        this.messageService = messageService;
        this.tabelaPrecoService = tabelaPrecoService;
        this.tabelaPrecoItemConverter = tabelaPrecoItemConverter;
        this.tabelaPrecoItemRepo = tabelaPrecoItemRepo;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID criar(@RequestBody TabelaPrecoItemDTO dto) {
        TabelaPrecoItem tabelaPrecoItem = tabelaPrecoItemConverter.createFrom(dto);
        return tabelaPrecoService.adicionarItem(tabelaPrecoItem).getId();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UUID alterar(@PathVariable UUID id, @RequestBody TabelaPrecoItemDTO dto) {
        TabelaPrecoItem tabelaPrecoItem = tabelaPrecoItemConverter.createFrom(dto);
        return tabelaPrecoService.alterarItem(tabelaPrecoItem).getId();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        tabelaPrecoService.removerItem(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public TabelaPrecoItemDTO obterPorId(@PathVariable UUID id) {
        TabelaPrecoItemDTO dto = tabelaPrecoItemConverter
                .createFrom(
                        tabelaPrecoItemRepo.findById(id)
                .orElseThrow(()-> new RegraNegocioException(messageService.getMessage("tabelaprecoitem.nao.encontrado", null))));
        return dto;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<TabelaPrecoItemDTO> obterPorExemplo(TabelaPrecoItemDTO filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING);

        Example example = Example.of(tabelaPrecoItemConverter.createFrom(filtro), matcher);

        List<TabelaPrecoItemDTO> resultado = tabelaPrecoItemConverter.createFromEntities(
                tabelaPrecoItemRepo.findAll(example));

        return resultado;
    }
}