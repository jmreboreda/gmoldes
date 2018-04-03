package gmoldes.domain.person.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "study")
@NamedQueries(value = {
        @NamedQuery(
                name = StudyVO.FIND_STUDY_BY_ID,
                query = "select p from StudyVO p where p.study_id = :code"
        )
})

public class StudyVO implements Serializable {

    public static final String FIND_STUDY_BY_ID = "StudyVO.FIND_STUDY_BY_ID";

    @Id
    @SequenceGenerator(name = "study_id_seq", sequenceName = "study_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "study_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    private Integer study_id;
    private String study_description;

    public StudyVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudy_id() {
        return study_id;
    }

    public void setStudy_id(Integer study_id) {
        this.study_id = study_id;
    }

    public String getStudy_description() {
        return study_description;
    }

    public void setStudy_description(String study_description) {
        this.study_description = study_description;
    }
}
