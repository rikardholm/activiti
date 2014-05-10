package rikardholm.insurance.infrastructure.postgres;

import org.apache.ibatis.annotations.*;
import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.Insurance;
import rikardholm.insurance.domain.InsuranceNumber;
import rikardholm.insurance.domain.internal.CustomerImpl;
import rikardholm.insurance.domain.internal.InsuranceImpl;

import java.util.List;

public interface InsuranceMapper {
    @Select("SELECT insurance_number, customer_id FROM insurances WHERE insurance_number=#{insuranceNumber}")
    @ConstructorArgs({
            @Arg(column = "insurance_number", javaType = InsuranceNumber.class),
            @Arg(column = "customer_id", javaType = CustomerImpl.class, select = "rikardholm.insurance.infrastructure.postgres.CustomerMapper.findById")
    })
    InsuranceImpl findByInsuranceNumber(InsuranceNumber insuranceNumber);

    @Select("SELECT insurance_number, customer_id FROM insurances i LEFT JOIN customers c ON i.customer_id=c.id WHERE c.personal_identifier=#{personalIdentifier}")
    @ConstructorArgs({
            @Arg(column = "insurance_number", javaType = InsuranceNumber.class),
            @Arg(column = "customer_id", javaType = CustomerImpl.class, select = "rikardholm.insurance.infrastructure.postgres.CustomerMapper.findById")
    })
    List<InsuranceImpl> findByCustomer(Customer customer);

    @Insert("INSERT INTO insurances (customer_id, insurance_number) VALUES (#{customerId},#{insurance.insuranceNumber})")
    void insert(@Param("insurance") Insurance insurance, @Param("customerId") Integer customerId);

    @Delete("DELETE FROM insurances WHERE insurance_number=#{insuranceNumber}")
    void delete(Insurance insurance);
}
