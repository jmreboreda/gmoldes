package gmoldes.domain.dto;

public class StudyDTO {

    private Integer id;
    private String studyLevelDescription;

    public StudyDTO(Integer id, String studyLevelDescription) {
        this.id = id;
        this.studyLevelDescription = studyLevelDescription;
    }

    public Integer getId() {
        return id;
    }

    public String getStudyLevelDescription() {
        return studyLevelDescription;
    }

    public static StudyDTO.StudyDTOBuilder create() {
        return new StudyDTO.StudyDTOBuilder();
    }

    public static class StudyDTOBuilder {

        private Integer id;
        private String studyLevelDescription;

        public StudyDTO.StudyDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public StudyDTO.StudyDTOBuilder withStudyLevelDescription(String studyLevelDescription) {
            this.studyLevelDescription = studyLevelDescription;
            return this;
        }

        public StudyDTO build() {
            return new StudyDTO(this.id, this.studyLevelDescription);
        }
    }
}
