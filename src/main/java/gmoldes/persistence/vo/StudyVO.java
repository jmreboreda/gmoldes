package gmoldes.persistence.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "estudios")
@NamedQueries(value = {
        @NamedQuery(
                name = StudyVO.FIND_STUDY_BY_ID,
                query = "select p from StudyVO p where p.idestudio = :code"
        )
})

public class StudyVO implements Serializable {

    public static final String FIND_STUDY_BY_ID = "StudyVO.FIND_STUDY_BY_ID";

    @Id
//    @SequenceGenerator(name = "typescontractvariations_id_seq", sequenceName = "typescontractvariations_id_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "typescontractvariations_id_seq")
    @Column(name = "idestudio", updatable = false)
    private Integer idestudio;
    private String descripest;

    public StudyVO() {
    }

    public Integer getIdestudio() {
        return idestudio;
    }

    public void setIdestudio(Integer idestudio) {
        this.idestudio = idestudio;
    }

    public String getDescripest() {
        return descripest;
    }

    public void setDescripest(String descripest) {
        this.descripest = descripest;
    }
}
