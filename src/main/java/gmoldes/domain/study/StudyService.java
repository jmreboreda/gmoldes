package gmoldes.domain.study;

import gmoldes.domain.study.dto.StudyDTO;

public class StudyService {

    private StudyService() {
    }

    public static class StudyServiceFactory {

        private static StudyService studyService;

        public static StudyService getInstance() {
            if(studyService == null) {
                studyService = new StudyService();
            }
            return studyService;
        }
    }

    public StudyDTO findStudyByStudyId(Integer studyId){
        StudyController studyController = new StudyController();

        return studyController.findStudyById(studyId);
    }
}
