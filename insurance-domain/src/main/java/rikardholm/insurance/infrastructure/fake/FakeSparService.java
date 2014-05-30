package rikardholm.insurance.infrastructure.fake;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import rikardholm.insurance.application.spar.SparResult;
import rikardholm.insurance.application.spar.SparService;
import rikardholm.insurance.application.spar.SparUnavailableException;
import rikardholm.insurance.domain.customer.PersonalIdentifier;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.tryFind;

public class FakeSparService implements SparService {
    private boolean available = true;
    private List<SparResult> sparResults = new ArrayList<SparResult>();

    @Override
    public Optional<SparResult> findBy(final PersonalIdentifier personalIdentifier) {
        if (!available) {
            throw new SparUnavailableException();
        }

        return tryFind(sparResults, new Predicate<SparResult>() {
            @Override
            public boolean apply(SparResult input) {
                return personalIdentifier.equals(input.personalIdentifier);
            }
        });
    }

    public void addSecret(PersonalIdentifier personalIdentifier) {
        sparResults.add(SparResult.secret(personalIdentifier));
    }

    public void add(PersonalIdentifier personalIdentifier, String address) {
        sparResults.add(SparResult.found(personalIdentifier, address));
    }

    public void makeUnavailable() {
         available = false;
    }

    public void makeAvailable() {
         available = true;
    }
}
