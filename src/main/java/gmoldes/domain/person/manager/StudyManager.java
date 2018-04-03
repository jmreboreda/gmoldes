package gmoldes.domain.person.manager;

import gmoldes.domain.dto.StudyDTO;
import gmoldes.domain.person.mapper.MapperStudyVODTO;
import gmoldes.persistence.dao.StudyDAO;
import gmoldes.persistence.vo.StudyVO;

public class StudyManager {

    public StudyManager() {
    }

    public StudyDTO findStudyById(Integer studyId){

        StudyDAO studyDAO = StudyDAO.StudyDAOFactory.getInstance();
        StudyVO studyVO = studyDAO.findStudyById(studyId);

        return new MapperStudyVODTO().mapStudyVODTO(studyVO);
    }
}
