package gmoldes.domain.contract.mapper;

import gmoldes.components.contract.new_contract.components.WorkDaySchedule;
import gmoldes.domain.contract_schedule.dto.ContractScheduleDTO;
import gmoldes.domain.contract_schedule.persistence.vo.ContractScheduleVO;
import gmoldes.domain.contractjsondata.ContractDayScheduleJsonData;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class MapperJsonScheduleToWorkDaySchedule {

    public static WorkDaySchedule map(ContractDayScheduleJsonData contractDayScheduleJsonData) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_TIME_FORMAT);

        LocalDate date = !contractDayScheduleJsonData.getDate().isEmpty() ? LocalDate.parse(contractDayScheduleJsonData.getDate(), dateFormatter) : null;
        LocalTime amFrom = !contractDayScheduleJsonData.getAmFrom().isEmpty() ? LocalTime.parse(contractDayScheduleJsonData.getAmFrom(), timeFormatter) :null;
        LocalTime amTo = !contractDayScheduleJsonData.getAmTo().isEmpty() ? LocalTime.parse(contractDayScheduleJsonData.getAmTo(), timeFormatter) :null;
        LocalTime pmFrom = !contractDayScheduleJsonData.getPmFrom().isEmpty() ? LocalTime.parse(contractDayScheduleJsonData.getPmFrom(), timeFormatter) :null;
        LocalTime pmTo = !contractDayScheduleJsonData.getPmTo().isEmpty() ? LocalTime.parse(contractDayScheduleJsonData.getPmTo(), timeFormatter) :null;
        Duration durationHours = Utilities.converterTimeStringToDuration(contractDayScheduleJsonData.getDurationHours());

        System.out.println("Horario -> día : " + contractDayScheduleJsonData.getDayOfWeek() + amFrom + " ::: " + amTo+ " ::: " + pmFrom + " ::: " + pmTo + " duración: " + durationHours.toString());

        return WorkDaySchedule.create()
                .withDayOfWeek(contractDayScheduleJsonData.getDayOfWeek())
                .withDate(date)
                .withAmFrom(amFrom)
                .withAmTo(amTo)
                .withPmFrom(pmFrom)
                .withPmTo(pmTo)
                .withDurationHours(durationHours)
                .build();

    }
}
