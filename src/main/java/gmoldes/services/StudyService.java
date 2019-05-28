package gmoldes.services;

import gmoldes.domain.study.StudyController;
import gmoldes.domain.study.dto.StudyDTO;

public class StudyService {

    public StudyService() {
    }

    public StudyDTO findStudyByStudyId(Integer studyId){
        StudyController studyController = new StudyController();

        return studyController.findStudyById(studyId);
    }
}
