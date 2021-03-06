package br.com.itarocha.hospedagem.service;

import br.com.itarocha.hospedagem.dto.EncaminhadorDTO;
import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.model.DestinacaoHospedagem;
import br.com.itarocha.hospedagem.model.Encaminhador;
import br.com.itarocha.hospedagem.model.Entidade;
import br.com.itarocha.hospedagem.repository.EncaminhadorRepository;
import br.com.itarocha.hospedagem.repository.EntidadeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EncaminhadorService {

    @Inject
    EncaminhadorRepository repositorio;

    @Inject
    EntidadeRepository repositoryEntidade;

    public EncaminhadorService(EncaminhadorRepository repositorio){
        this.repositorio = repositorio;
    }

    public Encaminhador create(EncaminhadorDTO model) {
        try{
            Encaminhador entity = new Encaminhador();
            entity.setId(model.getId());
            entity.setNome(model.getNome());
            entity.setCargo(model.getCargo());
            entity.setEmail(model.getEmail());
            entity.setTelefone(model.getTelefone());
            entity.setAtivo(model.getAtivo());

            if (model.getId() != null){
                Optional<Entidade> entidade = repositoryEntidade.findById(model.getEntidadeId());
                entity.setEntidade(entidade.get());
            }

            return repositorio.save(entity);
        }catch(Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
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
