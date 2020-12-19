package com.github.rafaelmdb.api;

import com.github.rafaelmdb.domain.entity.UF;
import com.github.rafaelmdb.domain.repo.UFRepo;
import com.github.rafaelmdb.domain.service.UFService;
import com.github.rafaelmdb.dto.UFDTO;
import com.github.rafaelmdb.dto.converters.UFConverter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/uf")
public class UFController {

    private final UFService ufService;
    private final UFConverter ufConverter;
    private final UFRepo ufRepo;
    private final QueryHelper<UF, UFDTO> query;

    public UFController(UFService ufService, UFConverter ufConverter, UFRepo ufRepo, QueryHelper<UF, UFDTO> query) {
        this.ufService = ufService;
        this.ufConverter = ufConverter;
        this.ufRepo = ufRepo;
        this.query = query;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID criar(@RequestBody UFDTO dto) {
        UF uf = ufConverter.createFrom(dto);
        return ufService.criar(uf).getId();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UUID alterar(@PathVariable UUID id, @RequestBody UFDTO dto) {
        UF uf = ufConverter.createFrom(dto);
        return ufService.alterar(uf).getId();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        ufService.remover(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UFDTO obterPorId(@PathVariable UUID id) {
        UFDTO dto = ufConverter
                .createFrom(
                        ufService.obterPorId(id));
        return dto;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<UFDTO> obterPorExemplo(UFDTO filtro, Integer pageNo, Integer pageSize, String sortBy){
        return query.obterPorExemplo(ufRepo, ufConverter, filtro, pageNo, pageSize, sortBy);
    }
}