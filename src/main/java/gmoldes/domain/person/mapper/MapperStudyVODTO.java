package gmoldes.domain.person.mapper;

import gmoldes.domain.dto.StudyDTO;
import gmoldes.persistence.vo.StudyVO;

public class MapperStudyVODTO {

    public StudyDTO mapStudyVODTO(StudyVO studyVO) {

        return StudyDTO.create()
                .withId(studyVO.getId())
                .withStudyId(studyVO.getStudy_id())
                .withDescription(studyVO.getStudy_description())
                .build();
    }
}
