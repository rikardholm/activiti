package rikardholm.insurance.transfer.task;

import com.google.common.base.Optional;
import org.activiti.engine.delegate.DelegateExecution;
import rikardholm.insurance.application.spar.SparResult;
import rikardholm.insurance.application.spar.SparService;
import rikardholm.insurance.application.spar.SparUnavailableException;
import rikardholm.insurance.domain.customer.PersonalIdentifier;

public class SparDelegate {

    private final SparService sparService;

    public SparDelegate(SparService sparService) {
        this.sparService = sparService;
    }

    public void lookup(DelegateExecution delegateExecution, PersonalIdentifier personalIdentifier) {
        Optional<SparResult> sparResult;
        try {
            sparResult = sparService.findBy(personalIdentifier);
        } catch (SparUnavailableException e) {
            delegateExecution.setVariable("foundPerson", false);
            delegateExecution.setVariable("secretAddress", false);
            delegateExecution.setVariable("lookupSuccessful", false);
            return;
        }

        if (!sparResult.isPresent()) {
            delegateExecution.setVariable("foundPerson", false);
            delegateExecution.setVariable("secretAddress", false);
            delegateExecution.setVariable("lookupSuccessful", true);
            return;
        }

        if (sparResult.get() instanceof SparResult.Found) {
            delegateExecution.setVariable("address", ((SparResult.Found) sparResult.get()).address);
            delegateExecution.setVariable("foundPerson", true);
            delegateExecution.setVariable("secretAddress", false);
            delegateExecution.setVariable("lookupSuccessful", true);
        } else {
            delegateExecution.setVariable("foundPerson", true);
            delegateExecution.setVariable("secretAddress", true);
            delegateExecution.setVariable("lookupSuccessful", true);
        }
    }
}
