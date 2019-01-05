package gmoldes.domain.study;

import gmoldes.domain.study.persistence.dao.StudyDAO;
import gmoldes.domain.study.dto.StudyDTO;
import gmoldes.domain.study.mappers.MapperStudyVODTO;
import gmoldes.domain.study.persistence.vo.StudyVO;

public class StudyManager {

    public StudyManager() {
    }

    public StudyDTO findStudyById(Integer studyId){

        StudyDAO studyDAO = StudyDAO.StudyDAOFactory.getInstance();
        StudyVO studyVO = studyDAO.findStudyById(studyId);

        return new MapperStudyVODTO().mapStudyVODTO(studyVO);
    }
}
