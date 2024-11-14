package com.example.github_jialin.data.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.github_jialin.ClientApplication
import com.example.github_jialin.data.model.RepoInfo

object DatabaseHelper : SQLiteOpenHelper(ClientApplication.context, "github_database", null, 1) {

    private const val TABLE_NAME1 = "repo_info"
    private const val COLUMN_ID = "id"
    private const val COLUMN_NAME = "name"
    private const val COLUMN_DESCRIPTION = "description"
    private const val COLUMN_LANGUAGE = "language"
    private const val COLUMN_STARS = "stars"
    private const val COLUMN_LOGIN = "login"
    private const val COLUMN_AVATAR_URL = "avatar_url"
    private const val COLUMN_URL = "url"
    private const val COLUMN_TIMESTAMP = "timestamp"

    private const val TABLE_NAME2 = "search_records"
    private const val COLUMN_KEYWORD = "keyword"

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery1 = "CREATE TABLE $TABLE_NAME1 ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, $COLUMN_URL TEXT, $COLUMN_LOGIN TEXT, $COLUMN_AVATAR_URL TEXT, " +
                "$COLUMN_DESCRIPTION TEXT, $COLUMN_LANGUAGE TEXT, $COLUMN_STARS INTEGER, $COLUMN_TIMESTAMP LONG)"

        val createTableQuery2 = "CREATE TABLE $TABLE_NAME2 ($COLUMN_KEYWORD TEXT, $COLUMN_LOGIN TEXT)"
        db.execSQL(createTableQuery1)
        db.execSQL(createTableQuery2)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }


    // 收藏相关
    fun insertRepo(repo: RepoInfo) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_ID, repo.id)
            put(COLUMN_NAME, repo.name)
            put(COLUMN_URL, repo.url)
            put(COLUMN_LOGIN, repo.login)
            put(COLUMN_AVATAR_URL, repo.avatarUrl)
            put(COLUMN_DESCRIPTION, repo.description)
            put(COLUMN_LANGUAGE, repo.language)
            put(COLUMN_STARS, repo.stars)
            put(COLUMN_TIMESTAMP, System.currentTimeMillis())
        }
        db.insert(TABLE_NAME1, null, contentValues)
        db.close()
    }

    fun isRepoExist(repo: RepoInfo): Boolean {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME1, null, "$COLUMN_ID =?", arrayOf(repo.id.toString()), null, null, null)
        val exist = cursor.count > 0
        cursor.close()
        db.close()
        return exist
    }

    @SuppressLint("Range")
    fun getReposByUser(user: String): List<RepoInfo> {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME1, null, "$COLUMN_LOGIN =?", arrayOf(user), null, null, "$COLUMN_TIMESTAMP ASC")
        val repos = mutableListOf<RepoInfo>()
        if (cursor.moveToFirst()) {
            do {
                val repo = RepoInfo(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LANGUAGE)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_STARS)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR_URL)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_URL)),
                )
                repos.add(repo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return repos
    }

    fun deleteRepo(user: String) {
        val db = writableDatabase
        db.delete(TABLE_NAME1, "$COLUMN_LOGIN =?", arrayOf(user))
        db.close()
    }

    //搜索记录相关


    @SuppressLint("Range")
    fun getSearchRecords(login: String): MutableList<String> {
        val db = readableDatabase
        val records = mutableListOf<String>()
        val cursor = db.query(TABLE_NAME2, null, "$COLUMN_LOGIN =?", arrayOf(login), null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val keyword = cursor.getString(cursor.getColumnIndex(COLUMN_KEYWORD))
                records.add(keyword)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return records
    }

    fun saveSearchRecord(login: String, searchKeyword: String) {
        val existingRecords = getSearchRecords(login)

        val db = writableDatabase
        if (existingRecords.contains(searchKeyword)) {
            val whereClause = "$COLUMN_KEYWORD =? AND $COLUMN_LOGIN =?"
            val whereArgs = arrayOf(searchKeyword, login)
            db.delete(TABLE_NAME2, whereClause, whereArgs)
        }

        val contentValues = ContentValues()
        contentValues.put(COLUMN_KEYWORD, searchKeyword)
        contentValues.put(COLUMN_LOGIN, login)
        db.insert(TABLE_NAME2, null, contentValues)

        if (existingRecords.size > 8) {
            val lastRecord = existingRecords.last()
            val whereClause = "$COLUMN_KEYWORD =? AND $COLUMN_LOGIN =?"
            val whereArgs = arrayOf(lastRecord, login)
            db.delete(TABLE_NAME2, whereClause, whereArgs)
        }
        db.close()
    }

    fun deleteSearchRecord(login: String) {
        val db = writableDatabase
        val whereClause = "$COLUMN_LOGIN =?"
        val whereArgs = arrayOf(login)
        db.delete(TABLE_NAME2, whereClause, whereArgs)
        db.close()
    }
}