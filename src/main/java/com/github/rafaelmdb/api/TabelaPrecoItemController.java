package com.github.rafaelmdb.api;

import com.github.rafaelmdb.domain.entity.TabelaPrecoItem;
import com.github.rafaelmdb.domain.repo.TabelaPrecoItemRepo;
import com.github.rafaelmdb.domain.service.TabelaPrecoItemService;
import com.github.rafaelmdb.dto.TabelaPrecoItemDTO;
import com.github.rafaelmdb.dto.converters.TabelaPrecoItemConverter;
import com.github.rafaelmdb.exception.RegraNegocioException;
import com.github.rafaelmdb.service.MessageService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/tabelapreco/{tabelaPrecoId}/itens")
public class TabelaPrecoItemController extends BaseController{
    private final TabelaPrecoItemService tabelaPrecoItemService;
    private final TabelaPrecoItemConverter tabelaPrecoItemConverter;
    private final TabelaPrecoItemRepo tabelaPrecoItemRepo;
    private final QueryHelper<TabelaPrecoItem> query;

    public TabelaPrecoItemController(MessageService messageService, TabelaPrecoItemService TabelaPrecoItemService, TabelaPrecoItemConverter tabelaPrecoItemConverter, TabelaPrecoItemRepo tabelaPrecoItemRepo, QueryHelper<TabelaPrecoItem> query) {
        super(messageService);
        this.tabelaPrecoItemService = TabelaPrecoItemService;
        this.tabelaPrecoItemConverter = tabelaPrecoItemConverter;
        this.tabelaPrecoItemRepo = tabelaPrecoItemRepo;
        this.query = query;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID criar(@RequestBody TabelaPrecoItemDTO dto) {
        TabelaPrecoItem tabelaPrecoItem = tabelaPrecoItemConverter.createFrom(dto);
        return tabelaPrecoItemService.adicionar(tabelaPrecoItem).getId();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UUID alterar(@PathVariable UUID id, @RequestBody TabelaPrecoItemDTO dto) {
        TabelaPrecoItem tabelaPrecoItem = tabelaPrecoItemConverter.createFrom(dto);
        return tabelaPrecoItemService.alterar(tabelaPrecoItem).getId();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        tabelaPrecoItemService.remover(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public TabelaPrecoItemDTO obterPorId(@PathVariable UUID id) {
        TabelaPrecoItemDTO dto = tabelaPrecoItemConverter
                .createFrom(tabelaPrecoItemService.obterPorId(id));
        return dto;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<TabelaPrecoItemDTO> obterPorExemplo(TabelaPrecoItemDTO filtro, Integer pageNo, Integer pageSize, String sortBy, HttpServletResponse response) {
        Page page = query.obterPorExemplo(tabelaPrecoItemRepo, tabelaPrecoItemConverter.createFrom(filtro), pageNo, pageSize, sortBy);
        return prepararRetornoListaPaginada(page, tabelaPrecoItemConverter, response);
    }
}