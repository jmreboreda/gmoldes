package gmoldes.domain.nie_nif.dto;

public class NieNifDTO {

    private String firstLetterNIF;
    private String numbersNIF;
    private String lastLetterNIF;

    public NieNifDTO(String firstLetterNIF, String numbersNIF, String lastLetterNIF) {
        this.firstLetterNIF = firstLetterNIF;
        this.numbersNIF = numbersNIF;
        this.lastLetterNIF = lastLetterNIF;
    }

    public String getFirstLetterNIF() {
        return firstLetterNIF;
    }

    public void setFirstLetterNIF(String firstLetterNIF) {
        this.firstLetterNIF = firstLetterNIF;
    }

    public String getNumbersNIF() {
        return numbersNIF;
    }

    public void setNumbersNIF(String numbersNIF) {
        this.numbersNIF = numbersNIF;
    }

    public String getLastLetterNIF() {
        return lastLetterNIF;
    }

    public void setLastLetterNIF(String lastLetterNIF) {
        this.lastLetterNIF = lastLetterNIF;
    }

    @Override
    public String toString(){
        return firstLetterNIF + numbersNIF + lastLetterNIF;
    }

    public static NieNifDTOBuilder create() {
        return new NieNifDTO.NieNifDTOBuilder();
    }

    public static class NieNifDTOBuilder {

        private String firstLetterNIF;
        private String numbersNIF;
        private String lastLetterNIF;

        public NieNifDTOBuilder withFirstLetterNIF(String firstLetterNIF) {
            this.firstLetterNIF = firstLetterNIF;
            return this;
        }

        public NieNifDTOBuilder withNumbersNIF(String numbersNIF) {
            this.numbersNIF = numbersNIF;
            return this;
        }

        public NieNifDTOBuilder withLastLetterNIF(String lastLetterNIF) {
            this.lastLetterNIF = lastLetterNIF;
            return this;
        }


        public NieNifDTO build() {
            return new NieNifDTO(this.firstLetterNIF, this.numbersNIF, this.lastLetterNIF);
        }
    }
}

