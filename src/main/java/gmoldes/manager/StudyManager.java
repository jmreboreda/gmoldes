package gmoldes.manager;

import gmoldes.domain.dto.ClientCCCDTO;
import gmoldes.domain.dto.StudyDTO;
import gmoldes.mappers.MapperStudyVODTO;
import gmoldes.persistence.dao.ClientCCCDAO;
import gmoldes.persistence.dao.StudyDAO;
import gmoldes.persistence.vo.ClientCCCVO;
import gmoldes.persistence.vo.StudyVO;

import java.util.ArrayList;
import java.util.List;

public class StudyManager {

    public StudyManager() {
    }

    public StudyDTO findStudyById(Integer studyId){

        StudyDAO studyDAO = StudyDAO.StudyDAOFactory.getInstance();
        StudyVO studyVO = studyDAO.findStudyById(studyId);

        return new MapperStudyVODTO().mapStudyVODTO(studyVO);
    }
}
