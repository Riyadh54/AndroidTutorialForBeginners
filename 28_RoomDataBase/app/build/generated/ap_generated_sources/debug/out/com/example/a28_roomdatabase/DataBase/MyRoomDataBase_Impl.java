package com.example.a28_roomdatabase.DataBase;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MyRoomDataBase_Impl extends MyRoomDataBase {
  private volatile CarsDetailsDAO _carsDetailsDAO;

  private volatile CarsPricesDAO _carsPricesDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `cars_table` (`_ID` INTEGER NOT NULL, `name` TEXT NOT NULL, `model` TEXT NOT NULL, `date` INTEGER NOT NULL, PRIMARY KEY(`_ID`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `cars_prices` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `price` REAL NOT NULL, `date` INTEGER NOT NULL, `carId` INTEGER NOT NULL, FOREIGN KEY(`carId`) REFERENCES `cars_table`(`_ID`) ON UPDATE CASCADE ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'c4c29dc141898cc0554c9bf171e61105')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `cars_table`");
        _db.execSQL("DROP TABLE IF EXISTS `cars_prices`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsCarsTable = new HashMap<String, TableInfo.Column>(4);
        _columnsCarsTable.put("_ID", new TableInfo.Column("_ID", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarsTable.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarsTable.put("model", new TableInfo.Column("model", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarsTable.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCarsTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCarsTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCarsTable = new TableInfo("cars_table", _columnsCarsTable, _foreignKeysCarsTable, _indicesCarsTable);
        final TableInfo _existingCarsTable = TableInfo.read(_db, "cars_table");
        if (! _infoCarsTable.equals(_existingCarsTable)) {
          return new RoomOpenHelper.ValidationResult(false, "cars_table(com.example.a28_roomdatabase.DataBase.CarsDetails).\n"
                  + " Expected:\n" + _infoCarsTable + "\n"
                  + " Found:\n" + _existingCarsTable);
        }
        final HashMap<String, TableInfo.Column> _columnsCarsPrices = new HashMap<String, TableInfo.Column>(4);
        _columnsCarsPrices.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarsPrices.put("price", new TableInfo.Column("price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarsPrices.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarsPrices.put("carId", new TableInfo.Column("carId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCarsPrices = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCarsPrices.add(new TableInfo.ForeignKey("cars_table", "CASCADE", "CASCADE",Arrays.asList("carId"), Arrays.asList("_ID")));
        final HashSet<TableInfo.Index> _indicesCarsPrices = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCarsPrices = new TableInfo("cars_prices", _columnsCarsPrices, _foreignKeysCarsPrices, _indicesCarsPrices);
        final TableInfo _existingCarsPrices = TableInfo.read(_db, "cars_prices");
        if (! _infoCarsPrices.equals(_existingCarsPrices)) {
          return new RoomOpenHelper.ValidationResult(false, "cars_prices(com.example.a28_roomdatabase.DataBase.CarsPrices).\n"
                  + " Expected:\n" + _infoCarsPrices + "\n"
                  + " Found:\n" + _existingCarsPrices);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "c4c29dc141898cc0554c9bf171e61105", "f77c70c588fcec34e3d6e3c77eadd6b5");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "cars_table","cars_prices");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `cars_table`");
      _db.execSQL("DELETE FROM `cars_prices`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CarsDetailsDAO.class, CarsDetailsDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(CarsPricesDAO.class, CarsPricesDAO_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public CarsDetailsDAO carsDetailsDAO() {
    if (_carsDetailsDAO != null) {
      return _carsDetailsDAO;
    } else {
      synchronized(this) {
        if(_carsDetailsDAO == null) {
          _carsDetailsDAO = new CarsDetailsDAO_Impl(this);
        }
        return _carsDetailsDAO;
      }
    }
  }

  @Override
  public CarsPricesDAO carsPricesDAO() {
    if (_carsPricesDAO != null) {
      return _carsPricesDAO;
    } else {
      synchronized(this) {
        if(_carsPricesDAO == null) {
          _carsPricesDAO = new CarsPricesDAO_Impl(this);
        }
        return _carsPricesDAO;
      }
    }
  }
}
