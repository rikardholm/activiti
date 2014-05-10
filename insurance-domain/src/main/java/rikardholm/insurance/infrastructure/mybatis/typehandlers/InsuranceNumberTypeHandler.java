package rikardholm.insurance.infrastructure.mybatis.typehandlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import rikardholm.insurance.domain.InsuranceNumber;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsuranceNumberTypeHandler extends BaseTypeHandler<InsuranceNumber> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, InsuranceNumber parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter.getValue());
    }

    @Override
    public InsuranceNumber getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return InsuranceNumber.of(rs.getLong(columnName));
    }

    @Override
    public InsuranceNumber getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return InsuranceNumber.of(rs.getLong(columnIndex));
    }

    @Override
    public InsuranceNumber getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return InsuranceNumber.of(cs.getLong(columnIndex));
    }
}
