package com.hybench;
/**
 *
 * @time 2023-03-04
 * @version 1.0.0
 * @file Constant.java
 * @description
 *      Define different database constant value.
 **/

public class Constant {
    final public static int DB_MYSQL = 3;
    final public static int DB_ORACLE = 4;
    final public static int DB_PG = 5;
    final public static int DB_UNKNOW = 0;
    
    public static final String POSTGRESQL = "postgresql";
    public static final String MYSQL = "mysql";
    public static final String ORACLE = "oracle";

    public static int getDbType(String db){

        if(POSTGRESQL.equalsIgnoreCase(db)){
            return Constant.DB_PG;
        }
        else if(db.equalsIgnoreCase(MYSQL)){
            return Constant.DB_MYSQL;
        }
        else if(db.equalsIgnoreCase(ORACLE)){
            return Constant.DB_ORACLE;
        }
        else{
            return Constant.DB_UNKNOW;
        }
    }
    
    public static boolean tp9NeedArg(int dbType) {
        return dbType == DB_PG;
    }
}
