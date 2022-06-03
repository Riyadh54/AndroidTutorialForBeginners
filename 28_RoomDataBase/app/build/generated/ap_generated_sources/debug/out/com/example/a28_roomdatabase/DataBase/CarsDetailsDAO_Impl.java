package com.example.a28_roomdatabase.DataBase;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CarsDetailsDAO_Impl implements CarsDetailsDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CarsDetails> __insertionAdapterOfCarsDetails;

  private final EntityDeletionOrUpdateAdapter<CarsDetails> __deletionAdapterOfCarsDetails;

  private final EntityDeletionOrUpdateAdapter<CarsDetails> __updateAdapterOfCarsDetails;

  private final SharedSQLiteStatement __preparedStmtOfDeleteCarByModel;

  public CarsDetailsDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCarsDetails = new EntityInsertionAdapter<CarsDetails>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `cars_table` (`_ID`,`name`,`model`,`date`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CarsDetails value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getModel() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getModel());
        }
        final Long _tmp = DateConverter.toLong(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
      }
    };
    this.__deletionAdapterOfCarsDetails = new EntityDeletionOrUpdateAdapter<CarsDetails>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `cars_table` WHERE `_ID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CarsDetails value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfCarsDetails = new EntityDeletionOrUpdateAdapter<CarsDetails>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `cars_table` SET `_ID` = ?,`name` = ?,`model` = ?,`date` = ? WHERE `_ID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CarsDetails value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getModel() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getModel());
        }
        final Long _tmp = DateConverter.toLong(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
        stmt.bindLong(5, value.getId());
      }
    };
    this.__preparedStmtOfDeleteCarByModel = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from cars_table where model=?";
        return _query;
      }
    };
  }

  @Override
  public void insertCar(final CarsDetails... carsDetails) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCarsDetails.insert(carsDetails);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCar(final CarsDetails... carsDetails) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCarsDetails.handleMultiple(carsDetails);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateCar(final CarsDetails... carsDetails) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCarsDetails.handleMultiple(carsDetails);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCarByModel(final String model) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCarByModel.acquire();
    int _argIndex = 1;
    if (model == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, model);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteCarByModel.release(_stmt);
    }
  }

  @Override
  public LiveData<List<CarsDetails>> getAllCars() {
    final String _sql = "select * from cars_table order by name asc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"cars_table"}, false, new Callable<List<CarsDetails>>() {
      @Override
      public List<CarsDetails> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "_ID");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfModel = CursorUtil.getColumnIndexOrThrow(_cursor, "model");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final List<CarsDetails> _result = new ArrayList<CarsDetails>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CarsDetails _item;
            _item = new CarsDetails();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpModel;
            if (_cursor.isNull(_cursorIndexOfModel)) {
              _tmpModel = null;
            } else {
              _tmpModel = _cursor.getString(_cursorIndexOfModel);
            }
            _item.setModel(_tmpModel);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp);
            _item.setDate(_tmpDate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<CarsDetails>> getCarsByName(final String name) {
    final String _sql = "select * from cars_table where name=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"cars_table"}, false, new Callable<List<CarsDetails>>() {
      @Override
      public List<CarsDetails> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "_ID");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfModel = CursorUtil.getColumnIndexOrThrow(_cursor, "model");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final List<CarsDetails> _result = new ArrayList<CarsDetails>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CarsDetails _item;
            _item = new CarsDetails();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpModel;
            if (_cursor.isNull(_cursorIndexOfModel)) {
              _tmpModel = null;
            } else {
              _tmpModel = _cursor.getString(_cursorIndexOfModel);
            }
            _item.setModel(_tmpModel);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp);
            _item.setDate(_tmpDate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<CarsDetails>> getCarsByModel(final String model) {
    final String _sql = "select * from cars_table where model like '%'||?||'%' ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (model == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, model);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"cars_table"}, false, new Callable<List<CarsDetails>>() {
      @Override
      public List<CarsDetails> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "_ID");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfModel = CursorUtil.getColumnIndexOrThrow(_cursor, "model");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final List<CarsDetails> _result = new ArrayList<CarsDetails>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CarsDetails _item;
            _item = new CarsDetails();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpModel;
            if (_cursor.isNull(_cursorIndexOfModel)) {
              _tmpModel = null;
            } else {
              _tmpModel = _cursor.getString(_cursorIndexOfModel);
            }
            _item.setModel(_tmpModel);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp);
            _item.setDate(_tmpDate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
