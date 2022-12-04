package com.winning.ants.core.base.dbops;

import com.winning.ants.core.exception.AntsException;
import com.winning.ants.core.utils.StringManager;
import com.winning.ants.model.datasource.DbType;

import java.util.HashMap;
import java.util.Map;

public class DbHelperChooser {
    private static final StringManager sm = StringManager.getManager(DbHelperChooser.class);
    private static final DbHelperChooser INSTANCE = new DbHelperChooser();

    private static final Map<DbType , IDbHelper> helperMap = new HashMap<>();


    public static void register(DbType type , IDbHelper dbHelper){
        helperMap.put(type ,dbHelper);
    }

    public static DbHelperChooser getInstance(){
        return INSTANCE;
    }

    private DbHelperChooser(){}

   public static IDbHelper choose(DbType type){
       IDbHelper dbHelper = helperMap.get(type);
       if(null == dbHelper){
          String msg = sm.getString("dbType.notSupport");
           throw new AntsException(msg);
       }
       return helperMap.get(type);
    }
}
