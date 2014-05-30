package rikardholm.insurance.application.spar;

import com.google.common.base.Optional;
import rikardholm.insurance.domain.customer.PersonalIdentifier;

public interface SparService {
    Optional<SparResult> findBy(PersonalIdentifier personalIdentifier);
}
