package br.com.itarocha.hospedagem.model.audit;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"},
        allowGetters = true
)
public abstract class UserDateAudit extends DateAudit {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5063288959434247691L;

	//@CreatedBy
	@Column(name = "created_by")
    private Long createdBy;

    //@LastModifiedBy
	@Column(name = "updated_by")
    private Long updatedBy;

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}