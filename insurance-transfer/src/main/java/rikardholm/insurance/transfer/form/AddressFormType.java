package rikardholm.insurance.transfer.form;

import org.activiti.engine.form.AbstractFormType;
import rikardholm.insurance.domain.customer.Address;

public class AddressFormType extends AbstractFormType {
    @Override
    public Object convertFormValueToModelValue(String propertyValue) {
        return Address.of(propertyValue);
    }

    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        if (modelValue == null) {
            return null;
        }

        if (modelValue instanceof Address) {
            return ((Address) modelValue).getValue();
        }

        return null;
    }

    @Override
    public String getName() {
        return "address";
    }
}
