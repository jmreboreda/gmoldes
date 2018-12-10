package gmoldes.components.timerecord.forms;

import gmoldes.utilities.Utilities;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class TimeRecord {

    private static final String TIME_RECORD_STRING_WITH_UNDERSCORE = "_Registro_Horario_";

    private Month nameOfMonth;
    private String yearNumber;
    private String enterpriseName;
    private String quoteAccountCode;
    private String employeeName;
    private String employeeNIF;
    private String numberHoursPerWeek;
    private String monthYearReceiptCopyText;

    public TimeRecord(Month nameOfMonth,
                      String yearNumber,
                      String enterpriseName,
                      String quoteAccountCode,
                      String employeeName,
                      String employeeNIF,
                      String numberHoursPerWeek,
                      String monthYearReceiptCopyText) {

        this.nameOfMonth = nameOfMonth;
        this.yearNumber = yearNumber;
        this.enterpriseName = enterpriseName;
        this.quoteAccountCode = quoteAccountCode;
        this.employeeName = employeeName;
        this.employeeNIF = employeeNIF;
        this.numberHoursPerWeek = numberHoursPerWeek;
        this.monthYearReceiptCopyText = monthYearReceiptCopyText;
    }

    public Month getNameOfMonth() {
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

    public String getMonthYearReceiptCopyText() {
        return monthYearReceiptCopyText;
    }

    public String toString(){
        return this.nameOfMonth.getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    public void setMonthYearReceiptCopyText(String monthYearReceiptCopyText) {
        this.monthYearReceiptCopyText = monthYearReceiptCopyText;
    }

    public String toFileName(){

        return Utilities.replaceWithUnderscore(this.getEnterpriseName())
                .concat(TIME_RECORD_STRING_WITH_UNDERSCORE)
                .concat(this.getNameOfMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()))
                .concat("_")
                .concat(this.getYearNumber())
                .concat("_")
                .concat(Utilities.replaceWithUnderscore(this.employeeName));
    }

    public static TimeRecord.TimeRecordBuilder create() {
        return new TimeRecord.TimeRecordBuilder();
    }

    public static class TimeRecordBuilder {

        private Month nameOfMonth;
        private String yearNumber;
        private String enterpriseName;
        private String quoteAccountCode;
        private String employeeName;
        private String employeeNIF;
        private String numberHoursPerWeek;
        private String monthYearReceiptCopyText;


        public TimeRecord.TimeRecordBuilder withNameOfMonth(Month nameOfMonth) {
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

        public TimeRecord.TimeRecordBuilder withMonthYearReceiptCopyTetx(String monthYearReceiptCopyText) {
            this.monthYearReceiptCopyText = monthYearReceiptCopyText;
            return this;
        }

        public TimeRecord build() {
            return new TimeRecord(this.nameOfMonth, this.yearNumber, this.enterpriseName, this.quoteAccountCode, this.employeeName,
                    this.employeeNIF, this.numberHoursPerWeek, this.monthYearReceiptCopyText);
        }
    }
}
