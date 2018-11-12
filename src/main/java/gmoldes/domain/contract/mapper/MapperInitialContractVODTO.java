package gmoldes.domain.contract.mapper;

import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

public class MapperInitialContractVODTO {

    public Set<DayOfWeek> mapDaysOfWeekToWorkVODTO(InitialContractVO initialContractVO) {
        String daysOfWork = initialContractVO.getContractJsonData().getDaysOfWeekToWork();

        Set<DayOfWeek> daysOfWeekSet = new HashSet<>();

        if (daysOfWork.contains(DayOfWeek.MONDAY.toString())) {
            daysOfWeekSet.add(DayOfWeek.MONDAY);
        }

        if (daysOfWork.contains(DayOfWeek.TUESDAY.toString())) {
            daysOfWeekSet.add(DayOfWeek.TUESDAY);
        }

        if (daysOfWork.contains(DayOfWeek.WEDNESDAY.toString())) {
            daysOfWeekSet.add(DayOfWeek.WEDNESDAY);
        }

        if (daysOfWork.contains(DayOfWeek.THURSDAY.toString())) {
            daysOfWeekSet.add(DayOfWeek.THURSDAY);
        }

        if (daysOfWork.contains(DayOfWeek.FRIDAY.toString())) {
            daysOfWeekSet.add(DayOfWeek.FRIDAY);
        }

        if (daysOfWork.contains(DayOfWeek.SATURDAY.toString())) {
            daysOfWeekSet.add(DayOfWeek.SATURDAY);
        }

        if (daysOfWork.contains(DayOfWeek.SUNDAY.toString())) {
            daysOfWeekSet.add(DayOfWeek.SUNDAY);
        }


        /** Ordenar y obtener el nombre local de los días de la semana
        Set<DayOfWeek> sortedDaysOfWeekSet = new TreeSet<DayOfWeek>(daysOfWeekSet);
        Locale locale = Locale.getDefault();
        for(DayOfWeek dayOfWeek : sortedDaysOfWeekSet) {
             System.out.println(dayOfWeek.getDisplayName(TextStyle.FULL, locale));
        }
         */

        /**
         // Get DayOfWeek from int value
         DayOfWeek dayOfWeek = DayOfWeek.of(1);
         System.out.println("dayOfWeek = " + dayOfWeek);

         // Get DayOfWeek from string value
         dayOfWeek = DayOfWeek.valueOf("SATURDAY");
         System.out.println("dayOfWeek = " + dayOfWeek);

         // Get DayOfWeek of a date object
         LocalDate date = LocalDate.now();
         DayOfWeek dow = date.getDayOfWeek();

         System.out.println("Date  = " + date);
         System.out.println("Dow   = " + dow + "; value = " + dow.getValue());

         // Get DayOfWeek display name in different locale.
         Locale locale = new Locale("id", "ID");
         String indonesian = dow.getDisplayName(TextStyle.SHORT, locale);
         System.out.println("ID = " + indonesian);

         String germany = dow.getDisplayName(TextStyle.FULL, Locale.GERMANY);
         System.out.println("DE = " + germany);

         // Adding number of days to DayOfWeek enum.
         System.out.println("DayOfWeek.MONDAY.plus(4) = " + DayOfWee

        */

        return daysOfWeekSet;

        }
}
