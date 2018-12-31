package gmoldes.domain.contractjsondata;

import java.util.Map;

public class ContractScheduleJsonData {

    private Map<String, ContractDayScheduleJsonData> schedule;

    public ContractScheduleJsonData() {
    }

    public ContractScheduleJsonData(Map<String, ContractDayScheduleJsonData> schedule) {
        this.schedule = schedule;
    }


    public Map<String, ContractDayScheduleJsonData> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, ContractDayScheduleJsonData> schedule) {
        this.schedule = schedule;
    }
}
