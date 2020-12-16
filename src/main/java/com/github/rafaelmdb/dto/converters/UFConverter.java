package com.github.rafaelmdb.dto.converters;

import com.github.rafaelmdb.converters.BaseConverter;
import com.github.rafaelmdb.domain.entity.UF;
import com.github.rafaelmdb.dto.UFDTO;
import org.springframework.stereotype.Component;

@Component
public class UFConverter extends BaseConverter<UF, UFDTO>{

    @Override
    protected UF DoCreateFrom(UFDTO dto){
        UF uF = new UF();
        uF.setId(dto.getId());
        uF.setSigla(dto.getSigla());
        return uF;
    }

    @Override
    protected UFDTO DoCreateFrom(UF uF){
        return UFDTO
                .builder()
                .id(uF.getId())
                .sigla(uF.getSigla())
                .build();
    }
}
