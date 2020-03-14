package br.com.itarocha.hospedagem.service;

import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.dto.SituacaoLeitoDTO;
import br.com.itarocha.hospedagem.model.SituacaoLeito;
import br.com.itarocha.hospedagem.repository.SituacaoLeitoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SituacaoLeitoService {

    @Inject
    SituacaoLeitoRepository repositorio;

    public SituacaoLeitoService(SituacaoLeitoRepository repositorio){
        this.repositorio = repositorio;
    }

    public SituacaoLeito create(SituacaoLeitoDTO model) {
        try{
            SituacaoLeito entity = new SituacaoLeito();
            entity.setId(model.getId());
            entity.setDescricao(model.getDescricao());
            entity.setDisponivel(model.getDisponivel());
            return repositorio.save(entity);
        }catch(Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public boolean remove(Long id) {
        return repositorio.remove(id);
    }

    public Optional<SituacaoLeito> find(Long id) {
        return repositorio.findById(id);
    }

    public List<SituacaoLeito> findAll() {
        return repositorio.getAll("descricao");
    }

    public List<SelectValueVO> listSelect() {
        return repositorio.getListSelect("id", "descricao", "descricao");
    }

}
