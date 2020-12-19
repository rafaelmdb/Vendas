package com.github.rafaelmdb.api;

import com.github.rafaelmdb.domain.entity.Produto;
import com.github.rafaelmdb.domain.repo.ProdutoRepo;
import com.github.rafaelmdb.dto.ProdutoDTO;
import com.github.rafaelmdb.domain.service.ProdutoService;
import com.github.rafaelmdb.dto.converters.ProdutoConverter;
import com.github.rafaelmdb.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/produto")
public class ProdutoController extends BaseController{
    private final ProdutoService produtoService;
    private final ProdutoRepo produtoRepo;
    private final ProdutoConverter produtoConverter;
    private final QueryHelper<Produto> query;

    public ProdutoController(MessageService messageService, ProdutoService produtoService, ProdutoConverter produtoConverter, ProdutoRepo produtoRepo, QueryHelper<Produto> query) {
        super(messageService);
        this.produtoService = produtoService;
        this.produtoConverter = produtoConverter;
        this.produtoRepo = produtoRepo;
        this.query = query;
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
        return produtoService.alterar(produto).getId();
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
    public List<ProdutoDTO> obterPorExemplo(ProdutoDTO filtro, Integer pageNo, Integer pageSize, String sortBy, final HttpServletResponse response) {
        Page page = query.obterPorExemplo(produtoRepo,
                produtoConverter.createFrom(filtro),
                pageNo,
                pageSize,
                sortBy);

        return prepararRetornoListaPaginada(page, produtoConverter, response);
    }
}