package rikardholm.insurance.infrastructure.postgres;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import rikardholm.insurance.domain.Insurance;
import rikardholm.insurance.domain.InsuranceNumber;
import rikardholm.insurance.domain.internal.InsuranceImpl;

public interface InsuranceMapper {
    @Select("SELECT insurance_number, customer_id FROM insurances WHERE insurance_number=#{insuranceNumber}")
    InsuranceImpl findBy(InsuranceNumber insuranceNumber);

    @Insert("INSERT INTO insurances (customer_id, ")
    void insert(Insurance insurance);

    void delete(Insurance insurance);
}
