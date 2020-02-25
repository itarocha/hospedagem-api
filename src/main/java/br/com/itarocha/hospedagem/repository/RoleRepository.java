package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.Role;
import br.com.itarocha.hospedagem.model.RoleName;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Optional;

@ApplicationScoped
public class RoleRepository extends AbstractRepository<Role, Long> {

    @Inject
    EntityManager em;

    public RoleRepository(EntityManager em) {
        super(em);
    }

    public Optional<Role> findByName(RoleName roleName){
        Role role = em.createQuery("FROM Role model where model.name = :name", Role.class)
                .setParameter("name", roleName)
                .getSingleResult();
        return Optional.of(role);
    }

}
