package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Cliente;
import com.github.rafaelmdb.domain.repo.ClienteRepo;
import com.github.rafaelmdb.exception.RegraNegocioException;
import com.github.rafaelmdb.service.BaseService;
import com.github.rafaelmdb.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.*;
import java.util.Set;
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
        try {
            clienteRepo.save(cliente);
        }
        catch(Exception e){
            e.printStackTrace();
        }

         return null;
    }

    @Override
    public Cliente alterar(Cliente cliente) {
        validarAlteracao(cliente.getId(), clienteRepo);
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
