package ao.creativemode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("anos_letivos")
public class AnosLetivos {

    @Id
    private Long id;

    @Column("ano_inicio")
    private Integer anoInicio;

    @Column("ano_fim")
    private Integer anoFim;
}
