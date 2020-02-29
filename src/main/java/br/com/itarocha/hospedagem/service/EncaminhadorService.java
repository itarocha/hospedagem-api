package br.com.itarocha.hospedagem.service;

import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.model.Encaminhador;
import br.com.itarocha.hospedagem.repository.EncaminhadorRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EncaminhadorService {

    @Inject
    EncaminhadorRepository repositorio;

    public EncaminhadorService(EncaminhadorRepository repositorio){
        this.repositorio = repositorio;
    }

    public Encaminhador create(Encaminhador model) {
        try{
            repositorio.save(model);
        }catch(Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
        return model;
    }

    public boolean remove(Long id) {
        return repositorio.remove(id);
    }

    public Encaminhador update(Encaminhador model) {
        Optional<Encaminhador> obj = find(model.getId());
        if (obj.isPresent()) {
            return repositorio.save(model);
        }
        return model;
    }

    public Optional<Encaminhador> find(Long id) {
        return repositorio.findById(id);
    }

    public List<Encaminhador> findAll(Long entidadeId) {
        return repositorio.findAll(entidadeId);
    }

    public List<SelectValueVO> listSelect(Long entidadeId) {
        return repositorio.listSelect(entidadeId);
    }

}
