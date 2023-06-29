package com.hybench.load;

import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

public class DataImportFactory {
    public static void loadData(Map<String, String> tableMaps, Connection conn, String dbType) throws Exception {
        DataImport dataImport = loadDataImport(dbType);
        if (!dataImport.needImport()) {
            return;
        }
        for (Map.Entry<String, String> entry: tableMaps.entrySet()) {
            dataImport.doDataImport(conn, entry.getKey(), entry.getValue());
        }
    }
    
    public static DataImport loadDataImport(String dbType) {
        ServiceLoader<DataImport> loader = ServiceLoader.load(DataImport.class);
        Optional<Provider<DataImport>> dataImportProvider = loader.stream().filter(
                p -> p.get().getDbType() != null && p.get().getDbType().equalsIgnoreCase(dbType)
        ).findAny();
        if (!dataImportProvider.isPresent()) {
            System.out.println("No autoloader, do nothing!");
            return defaultDataImport;
        }
        System.out.println("success get autoloader:" + dataImportProvider.get().get().getClass());
        return dataImportProvider.get().get();
    }
    
    public static DataImport defaultDataImport = new DataImport() {
        @Override
        public void doDataImport(final Connection conn, final String tableName, final String dataFile) throws Exception {
        
        }
    
        @Override
        public boolean needImport() {
            return false;
        }
    
        @Override
        public String getDbType() {
            return "UNKNOWN";
        }
    };
}
