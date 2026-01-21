package ao.creativemode.kixi.model;



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

    public AnosLetivos() {}

    public AnosLetivos(Long id, Integer anoInicio, Integer anoFim) {
        this.id = id;
        this.anoInicio = anoInicio;
        this.anoFim = anoFim;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnoInicio() {
        return anoInicio;
    }

    public void setAnoInicio(Integer anoInicio) {
        this.anoInicio = anoInicio;
    }

    public Integer getAnoFim() {
        return anoFim;
    }

    public void setAnoFim(Integer anoFim) {
        this.anoFim = anoFim;
    }
}
