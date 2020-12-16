package com.github.rafaelmdb.api;

import com.github.rafaelmdb.domain.entity.Cliente;
import com.github.rafaelmdb.domain.repo.ClienteRepo;
import com.github.rafaelmdb.domain.service.ClienteService;
import com.github.rafaelmdb.dto.ClienteDTO;
import com.github.rafaelmdb.dto.converters.ClienteConverter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteConverter clienteConverter;
    private final ClienteRepo clienteRepo;

    public ClienteController(ClienteService clienteService, ClienteConverter clienteConverter, ClienteRepo clienteRepo) {
        this.clienteService = clienteService;
        this.clienteConverter = clienteConverter;
        this.clienteRepo = clienteRepo;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID criar(@RequestBody ClienteDTO dto) {
        Cliente cliente = clienteConverter.createFrom(dto);
        return clienteService.criar(cliente).getId();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UUID alterar(@PathVariable UUID id, @RequestBody ClienteDTO dto) {
        Cliente cliente = clienteConverter.createFrom(dto);
        return clienteService.alterar(cliente).getId();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        clienteService.remover(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO obterPorId(@PathVariable UUID id) {
        ClienteDTO dto = clienteConverter
                .createFrom(
                        clienteService.obterPorId(id));
        return dto;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteDTO> obterPorExemplo(ClienteDTO filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING);

        Example example = Example.of(clienteConverter.createFrom(filtro), matcher);

        List<ClienteDTO> resultado = clienteConverter.createFromEntities(
                clienteRepo.findAll(example));

        return resultado;
    }
}