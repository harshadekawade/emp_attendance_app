package DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "OfficeAttendance.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val create = "CREATE TABLE Attendance_Table (ID INTEGER PRIMARY KEY AUTOINCREMENT, FName TEXT, date TEXT, status TEXT)"
        db.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Attendance_Table")
        onCreate(db)
    }

    fun insertData(FName: String, date: String, status: String): Boolean {
        val DB = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("FName", FName)
            put("date", date)
            put("status", status)
        }
        val result = DB.insert("Attendance_Table", null, contentValues)
        return result != -1L
    }

    fun getAllDetail(inpdate: String): Cursor {
        val db = this.readableDatabase
        val query = "SELECT * FROM Attendance_Table WHERE date = '$inpdate'"
        return db.rawQuery(query, null)
    }
}

