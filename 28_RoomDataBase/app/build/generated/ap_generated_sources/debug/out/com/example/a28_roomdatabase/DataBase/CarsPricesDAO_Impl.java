package com.example.a28_roomdatabase.DataBase;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
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
public final class CarsPricesDAO_Impl implements CarsPricesDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CarsPrices> __insertionAdapterOfCarsPrices;

  private final EntityDeletionOrUpdateAdapter<CarsPrices> __deletionAdapterOfCarsPrices;

  private final EntityDeletionOrUpdateAdapter<CarsPrices> __updateAdapterOfCarsPrices;

  public CarsPricesDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCarsPrices = new EntityInsertionAdapter<CarsPrices>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `cars_prices` (`id`,`price`,`date`,`carId`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CarsPrices value) {
        stmt.bindLong(1, value.getId());
        stmt.bindDouble(2, value.getPrice());
        final Long _tmp = DateConverter.toLong(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        stmt.bindLong(4, value.getCarId());
      }
    };
    this.__deletionAdapterOfCarsPrices = new EntityDeletionOrUpdateAdapter<CarsPrices>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `cars_prices` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CarsPrices value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfCarsPrices = new EntityDeletionOrUpdateAdapter<CarsPrices>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `cars_prices` SET `id` = ?,`price` = ?,`date` = ?,`carId` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CarsPrices value) {
        stmt.bindLong(1, value.getId());
        stmt.bindDouble(2, value.getPrice());
        final Long _tmp = DateConverter.toLong(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        stmt.bindLong(4, value.getCarId());
        stmt.bindLong(5, value.getId());
      }
    };
  }

  @Override
  public void insertPrice(final CarsPrices carsPrices) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCarsPrices.insert(carsPrices);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deletePrice(final CarsPrices carsPrices) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCarsPrices.handle(carsPrices);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updatePrice(final CarsPrices carsPrices) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCarsPrices.handle(carsPrices);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<CarsPrices>> getPriceByCarName(final Long carId) {
    final String _sql = "select * from cars_prices where carId=? order by date desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (carId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, carId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"cars_prices"}, false, new Callable<List<CarsPrices>>() {
      @Override
      public List<CarsPrices> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCarId = CursorUtil.getColumnIndexOrThrow(_cursor, "carId");
          final List<CarsPrices> _result = new ArrayList<CarsPrices>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CarsPrices _item;
            _item = new CarsPrices();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            _item.setPrice(_tmpPrice);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp);
            _item.setDate(_tmpDate);
            final long _tmpCarId;
            _tmpCarId = _cursor.getLong(_cursorIndexOfCarId);
            _item.setCarId(_tmpCarId);
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
  public LiveData<List<CarsPrices>> getCarPrices(final long carId, final Date from, final Date to) {
    final String _sql = "select * from cars_prices where carId=? AND date>=? AND date<=? order by date desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, carId);
    _argIndex = 2;
    final Long _tmp = DateConverter.toLong(from);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 3;
    final Long _tmp_1 = DateConverter.toLong(to);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"cars_prices"}, false, new Callable<List<CarsPrices>>() {
      @Override
      public List<CarsPrices> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCarId = CursorUtil.getColumnIndexOrThrow(_cursor, "carId");
          final List<CarsPrices> _result = new ArrayList<CarsPrices>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CarsPrices _item;
            _item = new CarsPrices();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            _item.setPrice(_tmpPrice);
            final Date _tmpDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp_2);
            _item.setDate(_tmpDate);
            final long _tmpCarId;
            _tmpCarId = _cursor.getLong(_cursorIndexOfCarId);
            _item.setCarId(_tmpCarId);
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
  public LiveData<List<CarsPrices>> getCarPrices(final Date from, final Date to) {
    final String _sql = "select * from cars_prices where date>=? AND date<=? order by date desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final Long _tmp = DateConverter.toLong(from);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1 = DateConverter.toLong(to);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"cars_prices"}, false, new Callable<List<CarsPrices>>() {
      @Override
      public List<CarsPrices> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCarId = CursorUtil.getColumnIndexOrThrow(_cursor, "carId");
          final List<CarsPrices> _result = new ArrayList<CarsPrices>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CarsPrices _item;
            _item = new CarsPrices();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            _item.setPrice(_tmpPrice);
            final Date _tmpDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp_2);
            _item.setDate(_tmpDate);
            final long _tmpCarId;
            _tmpCarId = _cursor.getLong(_cursorIndexOfCarId);
            _item.setCarId(_tmpCarId);
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
  public double getPricesSum(final long carId) {
    final String _sql = "select sum(price) from cars_prices where carId=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, carId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final double _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getDouble(0);
      } else {
        _result = 0.0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<CarsDetailsAndPrices>> getAllPricesSum() {
    final String _sql = "select sum(cars_prices.price) as _carPrice,cars_table.model as _carName from cars_prices INNER JOIN cars_table ON cars_prices.carId=cars_table._ID group by name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"cars_prices","cars_table"}, false, new Callable<List<CarsDetailsAndPrices>>() {
      @Override
      public List<CarsDetailsAndPrices> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCarPrice = 0;
          final int _cursorIndexOfCarName = 1;
          final List<CarsDetailsAndPrices> _result = new ArrayList<CarsDetailsAndPrices>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CarsDetailsAndPrices _item;
            _item = new CarsDetailsAndPrices();
            final double _tmp_carPrice;
            _tmp_carPrice = _cursor.getDouble(_cursorIndexOfCarPrice);
            _item.set_carPrice(_tmp_carPrice);
            final String _tmp_carName;
            if (_cursor.isNull(_cursorIndexOfCarName)) {
              _tmp_carName = null;
            } else {
              _tmp_carName = _cursor.getString(_cursorIndexOfCarName);
            }
            _item.set_carName(_tmp_carName);
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
