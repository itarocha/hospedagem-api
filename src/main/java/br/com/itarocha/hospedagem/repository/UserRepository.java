package br.com.itarocha.hospedagem.repository;

import br.com.itarocha.hospedagem.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository extends AbstractRepository<User, Long> {

    @Inject
    EntityManager em;

    public UserRepository(EntityManager em) {
        super(em);
    }

    public Optional<User> findByEmail(String email){
        User user = em.createQuery("FROM User model WHERE model.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
        return Optional.of(user);
    }

    public Optional<User> findByUsernameOrEmail(String username, String email){
        User user = em.createQuery("FROM User model WHERE model.username = :username OR model.email = :email", User.class)
                .setParameter("username", username)
                .setParameter("email", email)
                .getSingleResult();
        return Optional.of(user);
    }

    public List<User> findByIdIn(List<Long> userIds){
        return em.createQuery("FROM User model WHERE model.id IN :userIds")
                .setParameter("userIds", userIds)
                .getResultList();
    }

    public Optional<User> findByUsername(String username){
        User user = em.createQuery("FROM User model WHERE model.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
        return Optional.of(user);
    }

    public Boolean existsByUsername(String username){
        User user = em.createQuery("FROM User model WHERE model.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
        return user != null;
    }

    public Boolean existsByEmail(String email){
        User user = em.createQuery("FROM User model WHERE model.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
        return user != null;
    }
}
