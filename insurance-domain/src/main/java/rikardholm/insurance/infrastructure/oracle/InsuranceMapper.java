package rikardholm.insurance.infrastructure.oracle;

import java.util.List;

import org.apache.ibatis.annotations.*;

import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceNumber;
import rikardholm.insurance.domain.internal.CustomerImpl;
import rikardholm.insurance.domain.internal.InsuranceImpl;

public interface InsuranceMapper {
	@Select("SELECT insurance_number, customer_id FROM insurances WHERE insurance_number=#{insuranceNumber}")
	@ConstructorArgs({
			@Arg(column = "insurance_number", javaType = InsuranceNumber.class),
			@Arg(column = "customer_id", javaType = CustomerImpl.class, select = "rikardholm.insurance.infrastructure.oracle.CustomerMapper.findById")
	})
	InsuranceImpl findByInsuranceNumber(InsuranceNumber insuranceNumber);

	@Select("SELECT insurance_number, customer_id FROM insurances i LEFT JOIN customers c ON i.customer_id=c.id WHERE c.personal_identifier=#{personalIdentifier}")
	@ConstructorArgs({
			@Arg(column = "insurance_number", javaType = InsuranceNumber.class),
			@Arg(column = "customer_id", javaType = CustomerImpl.class, select = "rikardholm.insurance.infrastructure.oracle.CustomerMapper.findById")

	})
	List<InsuranceImpl> findByCustomer(Customer customer);

	@Insert("INSERT INTO insurances (id,insurance_number,customer_id) VALUES (#{id},#{insurance.insuranceNumber},#{customerId})")
	@SelectKey(statement = "SELECT insurances_seq.nextval FROM DUAL",
			keyProperty = "id",
			keyColumn = "id",
			resultType = Long.class, before = true
	)
	void insert(@Param("insurance") Insurance insurance, @Param("customerId") Integer customerId);

	@Delete("DELETE FROM insurances WHERE insurance_number=#{insuranceNumber}")
	void delete(Insurance insurance);
}
