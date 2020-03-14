package br.com.itarocha.hospedagem.repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import br.com.itarocha.hospedagem.dto.SelectValueVO;
import br.com.itarocha.hospedagem.model.IEntity;

//https://vertx.io/docs/vertx-mysql-client/java/
public abstract class AbstractRepository<Entity extends IEntity, PK extends Number> {

    private Class<Entity> entityClass;
    private EntityManager em;

    public AbstractRepository(){}

    public AbstractRepository(EntityManager em){
        this.em = em;
        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<Entity>) genericSuperClass.getActualTypeArguments()[0];
    }

    public Entity save(Entity e){
        if (e.getId() != null) {
            return em.merge(e);
        } else {
            em.persist(e);
            return e;
        }
    }

    public void delete(Entity e) {
        em.remove(e);
    }

    public boolean remove(PK id) {
        return findById(id)
                .map( model -> {
                    delete(model);
                    return true;
                }).orElse(false);
    }

    public Optional<Entity> findById(PK id){
        Entity e = em.find(entityClass, id);
        return Optional.ofNullable(e);
    }

    public List<Entity> getAll(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entity> q = cb.createQuery(entityClass);
        Root<Entity> c = q.from(entityClass);
        q.select(c);
        return em.createQuery(q).getResultList();
    }

    public List<Entity> getAll(String orderField){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entity> root = cb.createQuery(entityClass);
        Root<Entity> c = root.from(entityClass);
        root.select(c);
        root.orderBy(cb.asc(c.get(orderField)));
        return em.createQuery(root).getResultList();
    }

    public List<Entity> findByFieldNameAndValue(String campo, String valor, String orderBy){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entity> q = cb.createQuery(entityClass);
        Root<Entity> root = q.from(entityClass);

        Predicate p = cb.like(cb.lower(root.get(campo)),
                new StringBuilder()
                        .append("%")
                        .append(valor.toLowerCase())
                        .append("%")
                        .toString());
        q.select(root);
        q.where(p);
        q.orderBy(cb.asc(root.get(orderBy)));
        return em.createQuery(q).getResultList();
    }

    public Long count(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Entity> root = q.from(entityClass);
        q.select(cb.count(root));
        TypedQuery<Long> tq = em.createQuery(q);
        return tq.getSingleResult();
    }

    public List<SelectValueVO> getListSelect(String idField, String textField, String orderField ){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SelectValueVO> criteriaQuery = cb.createQuery(SelectValueVO.class);
        Root<Entity> root = criteriaQuery.from(entityClass);
        Path<Long> pathId = root.get(idField);
        Path<String> pathDescricao = root.get(textField);
        criteriaQuery.select(cb.construct(SelectValueVO.class, pathId, pathDescricao));
        criteriaQuery.orderBy(cb.asc(root.get(orderField)));
        return em.createQuery(criteriaQuery).getResultList();
    }

}
