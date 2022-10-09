package credit.advisory.model;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.sql.Types.OTHER;

public class EnumTypePostgres extends EnumType<Application.Status> {

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index,
                            SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, OTHER);
        } else {
            st.setObject(index, value.toString(), OTHER);
        }
    }


}
