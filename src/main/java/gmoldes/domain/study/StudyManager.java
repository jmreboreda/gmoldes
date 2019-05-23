package gmoldes.domain.study;

import gmoldes.domain.study.dto.StudyDTO;
import gmoldes.domain.study.mappers.MapperStudyVODTO;
import gmoldes.domain.study.persistence.dao.StudyDAO;
import gmoldes.domain.study.persistence.vo.StudyVO;

import java.util.ArrayList;
import java.util.List;

public class StudyManager {

    public StudyManager() {
    }

    public List<StudyDTO> findAllStudy(){
        List<StudyDTO> studyDTOList = new ArrayList<>();

        StudyDAO studyDAO = StudyDAO.StudyDAOFactory.getInstance();
        List<StudyVO> studyVOList = studyDAO.findAllStudy();

        for(StudyVO studyVO : studyVOList){
            StudyDTO studyDTO = StudyDTO.create()
                    .withId(studyVO.getId())
                    .withStudyId(studyVO.getStudy_id())
                    .withDescription(studyVO.getStudy_description())
                    .build();

            studyDTOList.add(studyDTO);
        }

        return studyDTOList;
    }

    public StudyDTO findStudyById(Integer studyId){
        StudyDAO studyDAO = StudyDAO.StudyDAOFactory.getInstance();
        StudyVO studyVO = studyDAO.findStudyById(studyId);

        return new MapperStudyVODTO().mapStudyVODTO(studyVO);
    }
}
