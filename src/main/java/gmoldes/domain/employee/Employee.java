package gmoldes.domain.employee;

import gmoldes.domain.contract.Contract;
import gmoldes.domain.person.Person;

import java.util.Set;

public class Employee extends Person {

    private Set<Contract> contracts;

    private Employee(){
        }

    public Employee(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }
}
