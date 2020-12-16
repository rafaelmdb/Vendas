package com.github.rafaelmdb.dto.converters;

import com.github.rafaelmdb.converters.BaseConverter;
import com.github.rafaelmdb.domain.entity.Cliente;
import com.github.rafaelmdb.dto.ClienteDTO;
import org.springframework.stereotype.Component;

@Component
public class ClienteConverter extends BaseConverter<Cliente, ClienteDTO>{

    @Override
    protected Cliente DoCreateFrom(ClienteDTO dto){
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNome(dto.getNome());
        cliente.setTipoPessoa(dto.getTipoPessoa());
        cliente.setCnpjCpf(dto.getCnpjcpf());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        return cliente;
    }

    @Override
    protected ClienteDTO DoCreateFrom(Cliente cliente){
        return ClienteDTO
                .builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .tipoPessoa(cliente.getTipoPessoa())
                .telefone(cliente.getTelefone())
                .email(cliente.getEmail())
                .build();
    }
}
