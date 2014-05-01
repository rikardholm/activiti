package rikardholm.insurance.service.spar;

import rikardholm.insurance.service.Optional;
import rikardholm.insurance.service.PersonalIdentifier;

public interface SparService {
    Optional<SparResult> findBy(PersonalIdentifier personalIdentifier);
}
