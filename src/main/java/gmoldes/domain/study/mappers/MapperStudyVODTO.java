package gmoldes.domain.study.mappers;

import gmoldes.domain.study.persistence.vo.StudyVO;
import gmoldes.domain.study.dto.StudyDTO;

public class MapperStudyVODTO {

    public StudyDTO mapStudyVODTO(StudyVO studyVO) {

        return StudyDTO.create()
                .withId(studyVO.getId())
                .withStudyId(studyVO.getStudy_id())
                .withDescription(studyVO.getStudy_description())
                .build();
    }
}
