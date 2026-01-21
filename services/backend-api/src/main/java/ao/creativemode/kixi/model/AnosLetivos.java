package ao.creativemode.kixi.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("anos_letivos")
public class AnosLetivos {

    @Id
    private Long id;

    @Column("ano_inicio")
    private Integer anoInicio;

    @Column("ano_fim")
    private Integer anoFim;

    @Column("deleted")
    private Boolean deleted = false;

    @Column("deleted_at")
    private LocalDateTime deletedAt;

    public AnosLetivos() {}

    public Long getId() {
        return id;
    }

    public Integer getAnoInicio() {
        return anoInicio;
    }

    public Integer getAnoFim() {
        return anoFim;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAnoInicio(Integer anoInicio) {
        this.anoInicio = anoInicio;
    }

    public void setAnoFim(Integer anoFim) {
        this.anoFim = anoFim;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
