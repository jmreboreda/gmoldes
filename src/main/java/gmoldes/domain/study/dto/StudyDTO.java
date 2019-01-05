package gmoldes.domain.study.dto;

public class StudyDTO {

    private Integer id;
    private Integer studyId;
    private String studyDescription;

    public StudyDTO(Integer id, Integer studyId, String studyDescription) {
        this.id = id;
        this.studyId = studyId;
        this.studyDescription = studyDescription;
    }

    public Integer getId() {
        return id;
    }

    public Integer getStudyId() {
        return studyId;
    }

    public String getStudyDescription() {
        return studyDescription;
    }

    public static StudyDTO.StudyDTOBuilder create() {
        return new StudyDTO.StudyDTOBuilder();
    }

    public static class StudyDTOBuilder {

        private Integer id;
        private Integer studyId;
        private String studyDescription;

        public StudyDTO.StudyDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public StudyDTO.StudyDTOBuilder withStudyId(Integer study_id) {
            this.studyId = study_id;
            return this;
        }
        public StudyDTO.StudyDTOBuilder withDescription(String studyLevelDescription) {
            this.studyDescription = studyLevelDescription;
            return this;
        }

        public StudyDTO build() {
            return new StudyDTO(this.id, this.studyId, this.studyDescription);
        }
    }
}
