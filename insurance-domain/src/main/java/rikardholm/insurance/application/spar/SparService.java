package rikardholm.insurance.application.spar;

import rikardholm.insurance.common.Optional;
import rikardholm.insurance.domain.PersonalIdentifier;

public interface SparService {
    Optional<SparResult> findBy(PersonalIdentifier personalIdentifier);
}
