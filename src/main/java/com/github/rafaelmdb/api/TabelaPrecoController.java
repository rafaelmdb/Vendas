package com.github.rafaelmdb.api;

import com.github.rafaelmdb.domain.entity.TabelaPreco;
import com.github.rafaelmdb.domain.repo.TabelaPrecoRepo;
import com.github.rafaelmdb.domain.service.TabelaPrecoService;
import com.github.rafaelmdb.dto.TabelaPrecoDTO;
import com.github.rafaelmdb.dto.converters.TabelaPrecoConverter;
import com.github.rafaelmdb.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/tabelapreco")
public class TabelaPrecoController extends BaseController{

    private final TabelaPrecoService tabelaPrecoService;
    private final TabelaPrecoConverter tabelaPrecoConverter;
    private final TabelaPrecoRepo tabelaPrecoRepo;
    private final QueryHelper<TabelaPreco> query;

    public TabelaPrecoController(MessageService messageService, TabelaPrecoService tabelaPrecoService, TabelaPrecoConverter tabelaPrecoConverter, TabelaPrecoRepo tabelaPrecoRepo, QueryHelper<TabelaPreco> query) {
        super(messageService);
        this.tabelaPrecoService = tabelaPrecoService;
        this.tabelaPrecoConverter = tabelaPrecoConverter;
        this.tabelaPrecoRepo = tabelaPrecoRepo;
        this.query = query;
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
    public List<TabelaPrecoDTO> obterPorExemplo(TabelaPrecoDTO filtro, Integer pageNo, Integer pageSize, String sortBy, HttpServletResponse response) {
        Page page =  query.obterPorExemplo(tabelaPrecoRepo, tabelaPrecoConverter.createFrom(filtro), pageNo, pageSize, sortBy);
        return prepararRetornoListaPaginada(page, tabelaPrecoConverter, response);
    }
}