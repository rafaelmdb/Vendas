package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Cliente;
import com.github.rafaelmdb.domain.repo.ClienteRepo;
import com.github.rafaelmdb.exception.RegraNegocioException;
import com.github.rafaelmdb.service.BaseService;
import com.github.rafaelmdb.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin2.message.Message;

import java.util.UUID;

@Service
@Slf4j
public class ClienteServiceImpl extends BaseService implements ClienteService {
    private final ClienteRepo clienteRepo;

    public ClienteServiceImpl(MessageService messageService, ClienteRepo clienteRepo){
        super(messageService);
        this.clienteRepo = clienteRepo;
    }

    @Override
    public Cliente criar(Cliente cliente){
        validarCliente(cliente);
        return clienteRepo.save(cliente);
    }

    private void validarCliente(Cliente cliente){
        getMessageService().validar(Strings.isEmpty(cliente.getNome()), "nome.obrigatorio");
    }

    @Override
    public Cliente alterar(Cliente cliente) {
        validarAlteracao(cliente.getId(), clienteRepo);
        validarCliente(cliente);
        return clienteRepo.save(cliente);
    }

    public Cliente obterPorId(UUID id) {
        return clienteRepo
                .findById(id)
                .orElseThrow(()->new RegraNegocioException(getMessageService().getMessage("cliente.nao.encontrado", null)));
    }

    @Override
    public void remover(UUID id) {
        Cliente cliente = obterPorId(id);
        clienteRepo.delete(cliente);
    }
}
