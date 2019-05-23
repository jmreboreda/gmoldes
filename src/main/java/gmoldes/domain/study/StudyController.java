package gmoldes.domain.study;

import gmoldes.domain.study.dto.StudyDTO;

import java.util.List;

public class StudyController {

    public void StudyController(){

    }

    public StudyDTO findStudyById(Integer studyId){
        StudyManager studyManager = new StudyManager();

        return studyManager.findStudyById(studyId);
    }

    public List<StudyDTO> findAllStudy(){
        StudyManager studyManager = new StudyManager();

        return studyManager.findAllStudy();
    }
}
