package rikardholm.insurance.service.spar.internal;

import com.google.common.base.Predicate;
import rikardholm.insurance.common.Optional;
import rikardholm.insurance.common.OptionalConverter;
import rikardholm.insurance.service.PersonalIdentifier;
import rikardholm.insurance.service.spar.SparResult;
import rikardholm.insurance.service.spar.SparService;
import rikardholm.insurance.service.spar.SparUnavailableException;

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

        return OptionalConverter.convert(tryFind(sparResults, new Predicate<SparResult>() {
            @Override
            public boolean apply(SparResult input) {
                return personalIdentifier.equals(input.personalIdentifier);
            }
        }));
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
