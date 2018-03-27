package gmoldes.mappers;

import gmoldes.domain.dto.StudyDTO;
import gmoldes.persistence.vo.StudyVO;

public class MapperStudyVODTO {

    public StudyDTO mapStudyVODTO(StudyVO studyVO) {

        return StudyDTO.create()
                .withId(studyVO.getIdestudio())
                .withStudyLevelDescription(studyVO.getDescripest())
                .build();
    }
}
