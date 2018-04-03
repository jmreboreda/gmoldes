package gmoldes.domain.person.mapper;

import gmoldes.domain.person.dto.StudyDTO;
import gmoldes.domain.person.persistence.vo.StudyVO;

public class MapperStudyVODTO {

    public StudyDTO mapStudyVODTO(StudyVO studyVO) {

        return StudyDTO.create()
                .withId(studyVO.getId())
                .withStudyId(studyVO.getStudy_id())
                .withDescription(studyVO.getStudy_description())
                .build();
    }
}
