package gmoldes.domain.employer;

import gmoldes.domain.client.Client;
import gmoldes.domain.contract.Contract;

import java.util.Set;

public class Employer extends Client {

    private Set<String> quoteAccountCodes;
    private Set<Contract> contracts;

    public Employer() {
    }

    public Employer(Set<String> quoteAccountCodes, Set<Contract> contracts) {
        this.quoteAccountCodes = quoteAccountCodes;
        this.contracts = contracts;
    }

    public Set<String> getQuoteAccountCodes() {
        return quoteAccountCodes;
    }

    public void setQuoteAccountCode(Set<String> quoteAccountCodes) {
        this.quoteAccountCodes = quoteAccountCodes;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public String toString(){

        return getAlphabeticalOrderName();
    }
}
