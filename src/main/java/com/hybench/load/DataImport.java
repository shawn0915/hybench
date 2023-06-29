package com.hybench.load;

import java.sql.Connection;

public interface DataImport {
    /**
     * Do data import.
     * @param tableName  the destination  table
     * @param dataFile the table's data file which to import, must absolutely path
     * @return void not to return
     * @throws Exception get any exception will throw out
     */
    void doDataImport(Connection conn, String tableName, String dataFile) throws Exception;
    default boolean needImport() {
        return true;
    }
    String getDbType();
}
