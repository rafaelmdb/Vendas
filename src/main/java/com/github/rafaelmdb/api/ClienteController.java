package com.github.rafaelmdb.api;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.github.rafaelmdb.domain.entity.Cliente;
import com.github.rafaelmdb.domain.repo.ClienteRepo;
import com.github.rafaelmdb.domain.service.ClienteService;
import com.github.rafaelmdb.dto.ClienteDTO;
import com.github.rafaelmdb.dto.converters.ClienteConverter;
import com.github.rafaelmdb.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/cliente")
public class ClienteController extends BaseController {
    private final ClienteService clienteService;
    private final ClienteConverter clienteConverter;
    private final ClienteRepo clienteRepo;
    private final QueryHelper<Cliente> query;

    public ClienteController(MessageService messageService, ClienteService clienteService, ClienteConverter clienteConverter, ClienteRepo clienteRepo, QueryHelper<Cliente> query) {
        super(messageService);
        this.clienteService = clienteService;
        this.clienteConverter = clienteConverter;
        this.clienteRepo = clienteRepo;
        this.query = query;
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
    public List<ClienteDTO> obterPorExemplo(ClienteDTO filtro, Integer pageNo, Integer pageSize, String sortBy, final HttpServletResponse response){
        Page page= query.obterPorExemplo(clienteRepo, clienteConverter.createFrom(filtro), pageNo, pageSize, sortBy);
        return prepararRetornoListaPaginada(page, clienteConverter, response);
    }
}