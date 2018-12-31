package gmoldes.domain.contractjsondata;

import java.util.Set;

public class ContractScheduleJsonData {

    private Set<ContractDayScheduleJsonData> schedule;

    public ContractScheduleJsonData(Set<ContractDayScheduleJsonData> schedule) {
        this.schedule = schedule;
    }

    public ContractScheduleJsonData() {
    }
    public Set<ContractDayScheduleJsonData> getSchedule() {
        return schedule;
    }

    public void setSchedule(Set<ContractDayScheduleJsonData> schedule) {
        this.schedule = schedule;
    }
}
