package br.com.itarocha.hospedagem.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="user_roles")
public class UserRoles {

    @EmbeddedId
    private UserRolesId id;

    public UserRolesId getId() {
        return id;
    }

    public void setId(UserRolesId id) {
        this.id = id;
    }

}

