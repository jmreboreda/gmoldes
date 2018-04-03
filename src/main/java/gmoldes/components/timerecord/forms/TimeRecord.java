package gmoldes.components.timerecord.forms;

import gmoldes.utilities.Utilities;

public class TimeRecord {

    private String nameOfMonth;
    private String yearNumber;
    private String enterpriseName;
    private String quoteAccountCode;
    private String employeeName;
    private String employeeNIF;
    private String numberHoursPerWeek;

    public TimeRecord(String nameOfMonth,
                      String yearNumber,
                      String enterpriseName,
                      String quoteAccountCode,
                      String employeeName,
                      String employeeNIF,
                      String numberHoursPerWeek) {

        this.nameOfMonth = nameOfMonth;
        this.yearNumber = yearNumber;
        this.enterpriseName = enterpriseName;
        this.quoteAccountCode = quoteAccountCode;
        this.employeeName = employeeName;
        this.employeeNIF = employeeNIF;
        this.numberHoursPerWeek = numberHoursPerWeek;
    }

    public String getNameOfMonth() {
        return nameOfMonth;
    }

    public String getYearNumber() {
        return yearNumber;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public String getQuoteAccountCode() {
        return quoteAccountCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeNIF() {
        return employeeNIF;
    }

    public String getNumberHoursPerWeek() {
        return numberHoursPerWeek;
    }

    public String toFileName(){

        return Utilities.replaceWithUnderscore(this.getEnterpriseName())
                .concat("_Registro_Horario_")
                .concat(this.getNameOfMonth())
                .concat("_")
                .concat(this.getYearNumber())
                .concat("_")
                .concat(Utilities.replaceWithUnderscore(this.employeeName));
    }

    public static TimeRecord.TimeRecordBuilder create() {
        return new TimeRecord.TimeRecordBuilder();
    }

    public static class TimeRecordBuilder {

        private String nameOfMonth;
        private String yearNumber;
        private String enterpriseName;
        private String quoteAccountCode;
        private String employeeName;
        private String employeeNIF;
        private String numberHoursPerWeek;

        public TimeRecord.TimeRecordBuilder withNameOfMonth(String nameOfMonth) {
            this.nameOfMonth = nameOfMonth;
            return this;
        }

        public TimeRecord.TimeRecordBuilder withYearNumber(String yearNumber) {
            this.yearNumber = yearNumber;
            return this;
        }

        public TimeRecord.TimeRecordBuilder withEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
            return this;
        }

        public TimeRecord.TimeRecordBuilder withQuoteAccountCode(String quoteAccountCode) {
            this.quoteAccountCode = quoteAccountCode;
            return this;
        }

        public TimeRecord.TimeRecordBuilder withEmployeeName(String employeeName) {
            this.employeeName = employeeName;
            return this;
        }

        public TimeRecord.TimeRecordBuilder withEmployeeNIF(String employeeNIF) {
            this.employeeNIF = employeeNIF;
            return this;
        }

        public TimeRecord.TimeRecordBuilder withNumberHoursPerWeek(String numberHoursPerWeek) {
            this.numberHoursPerWeek = numberHoursPerWeek;
            return this;
        }

        public TimeRecord build() {
            return new TimeRecord(this.nameOfMonth, this.yearNumber, this.enterpriseName, this.quoteAccountCode, this.employeeName,
                    this.employeeNIF, this.numberHoursPerWeek);
        }
    }
}
