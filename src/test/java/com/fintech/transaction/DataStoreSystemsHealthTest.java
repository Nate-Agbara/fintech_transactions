package com.fintech.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author: Nathan
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DataStoreSystemsHealthTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void dbPrimaryIsOk(){
        try {
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            String catalogName = dataSource.getConnection().getCatalog();

            assertNotNull(metaData.nullPlusNonNullIsNull());
            assertEquals("fintech", catalogName);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
