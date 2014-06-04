package rikardholm.insurance.transfer.form;

import org.activiti.engine.form.AbstractFormType;
import rikardholm.insurance.domain.customer.PersonalIdentifier;

public class PersonalIdentifierFormType extends AbstractFormType {
    @Override
    public Object convertFormValueToModelValue(String propertyValue) {
        return PersonalIdentifier.of(propertyValue);
    }

    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        if (modelValue == null) {
            return null;
        }

        if (modelValue instanceof PersonalIdentifier) {
            return ((PersonalIdentifier) modelValue).getValue();
        }

        return null;
    }

    @Override
    public String getName() {
        return "personalidentifier";
    }
}
