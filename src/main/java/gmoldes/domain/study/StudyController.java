package gmoldes.domain.study;

import gmoldes.domain.study.dto.StudyDTO;

import java.util.List;

public class StudyController {

    public void StudyController(){

    }

    public List<StudyDTO> findAllStudy(){

        StudyManager studyManager = new StudyManager();
        return studyManager.findAllStudy();
    }
}
