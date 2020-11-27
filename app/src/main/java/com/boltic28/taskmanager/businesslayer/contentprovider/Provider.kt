package com.boltic28.taskmanager.businesslayer.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.room.Room
import com.boltic28.taskmanager.datalayer.room.AppDataBase

class Provider : ContentProvider() {

    companion object {
        const val AUTH = "com.boltic28.taskmanager"

        const val SET_OF_ITEM = "vnd.android.cursor.dir"
        const val SINGLE_ITEM = "vnd.android.cursor.item"

        const val CODE_GOAL_ITEM = 101
        const val CODE_STEP_ITEM = 201
        const val CODE_TASK_ITEM = 301
        const val CODE_IDEA_ITEM = 401
        const val CODE_KEY_ITEM = 501

        const val CODE_GOAL_ALL = 102
        const val CODE_STEP_ALL = 202
        const val CODE_TASK_ALL = 302
        const val CODE_IDEA_ALL = 402
        const val CODE_KEY_ALL = 502
    }

    private var uriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        uriMatcher.addURI(AUTH, TABLE_GOALS, CODE_GOAL_ALL)
        uriMatcher.addURI(AUTH, "$TABLE_GOALS/#", CODE_GOAL_ITEM)
        uriMatcher.addURI(AUTH, TABLE_STEPS, CODE_STEP_ALL)
        uriMatcher.addURI(AUTH, "$TABLE_STEPS/#", CODE_STEP_ITEM)
        uriMatcher.addURI(AUTH, TABLE_TASKS, CODE_TASK_ALL)
        uriMatcher.addURI(AUTH, "$TABLE_TASKS/#", CODE_TASK_ITEM)
        uriMatcher.addURI(AUTH, TABLE_IDEAS, CODE_IDEA_ALL)
        uriMatcher.addURI(AUTH, "$TABLE_IDEAS/#", CODE_IDEA_ITEM)
        uriMatcher.addURI(AUTH, TABLE_KEYS, CODE_KEY_ALL)
        uriMatcher.addURI(AUTH, "$TABLE_KEYS/#", CODE_KEY_ITEM)
    }

    private lateinit var db: AppDataBase

    override fun onCreate(): Boolean {
        db = Room
            .databaseBuilder(context!!, AppDataBase::class.java, AppDataBase.DB_NAME)
            .build()

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectArgs: Array<out String>?,
        sort: String?
    ): Cursor? {
        Log.d("CPSA","query")
        val cursor: Cursor
        val code = uriMatcher.match(uri)

        val goalDao = db.goalDao()
        val stepDao = db.stepDao()
        val taskDao = db.taskDao()
        val ideaDao = db.ideaDao()
        val keyDao = db.keyDao()

        cursor = when (code) {
            CODE_GOAL_ALL -> goalDao.readAllForContentProvider()
            CODE_STEP_ALL -> stepDao.readAllForContentProvider()
            CODE_TASK_ALL -> taskDao.readAllForContentProvider()
            CODE_IDEA_ALL -> ideaDao.readAllForContentProvider()
            CODE_KEY_ALL -> keyDao.readAllForContentProvider()
            CODE_GOAL_ITEM -> goalDao.readByIdForContentProvider(ContentUris.parseId(uri))
            CODE_STEP_ITEM -> stepDao.readByIdForContentProvider(ContentUris.parseId(uri))
            CODE_TASK_ITEM -> taskDao.readByIdForContentProvider(ContentUris.parseId(uri))
            CODE_IDEA_ITEM -> ideaDao.readByIdForContentProvider(ContentUris.parseId(uri))
            CODE_KEY_ITEM -> keyDao.readByIdForContentProvider(ContentUris.parseId(uri))
            else -> throw IllegalArgumentException("Unknown URI: $uri -> $code")
        }

        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            CODE_GOAL_ALL -> "$SET_OF_ITEM/$AUTH.$TABLE_GOALS"
            CODE_STEP_ALL -> "$SET_OF_ITEM/$AUTH.$TABLE_STEPS"
            CODE_TASK_ALL -> "$SET_OF_ITEM/$AUTH.$TABLE_TASKS"
            CODE_IDEA_ALL -> "$SET_OF_ITEM/$AUTH.$TABLE_IDEAS"
            CODE_KEY_ALL -> "$SET_OF_ITEM/$AUTH.$TABLE_KEYS"
            CODE_GOAL_ITEM -> "$SINGLE_ITEM/$AUTH.$TABLE_GOALS"
            CODE_STEP_ITEM -> "$SINGLE_ITEM/$AUTH.$TABLE_STEPS"
            CODE_TASK_ITEM -> "$SINGLE_ITEM/$AUTH.$TABLE_TASKS"
            CODE_IDEA_ITEM -> "$SINGLE_ITEM/$AUTH.$TABLE_IDEAS"
            CODE_KEY_ITEM -> "$SINGLE_ITEM/$AUTH.$TABLE_KEYS"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d("CPSA","insert")
        values?.let {
            when (uriMatcher.match(uri)) {
                CODE_GOAL_ITEM -> {
                    db.goalDao().insertFromContentProvider(goalEntityFromContentValues(it))
                }
                CODE_STEP_ITEM -> {
                    db.stepDao().insertFromContentProvider(stepEntityFromContentValues(it))
                }
                CODE_TASK_ITEM -> {
                    db.taskDao().insertFromContentProvider(taskEntityFromContentValues(it))
                }
                CODE_IDEA_ITEM -> {
                    db.ideaDao().insertFromContentProvider(ideaEntityFromContentValues(it))
                }
                CODE_KEY_ITEM -> {
                    db.keyDao().insertFromContentProvider(keyEntityFromContentValues(it))
                }
                else -> throw IllegalArgumentException("Unknown URI: $uri -> ${uriMatcher.match(uri)}")
            }
        }
        return null
    }

    override fun delete(uri: Uri, p1: String?, p2: Array<out String>?): Int {
        Log.d("CPSA","delete")
        val deleted: Int
        when (uriMatcher.match(uri)) {
            CODE_GOAL_ITEM -> {
                deleted = db.goalDao().deleteByIdFromContentProvider(ContentUris.parseId(uri))
            }
            CODE_STEP_ITEM -> {
                deleted = db.stepDao().deleteByIdFromContentProvider(ContentUris.parseId(uri))
            }
            CODE_TASK_ITEM -> {
                deleted = db.taskDao().deleteByIdFromContentProvider(ContentUris.parseId(uri))
            }
            CODE_IDEA_ITEM -> {
                deleted = db.ideaDao().deleteByIdFromContentProvider(ContentUris.parseId(uri))
            }
            CODE_KEY_ITEM -> {
                deleted = db.keyDao().deleteByIdFromContentProvider(ContentUris.parseId(uri))
            }
            CODE_GOAL_ALL -> {
                deleted = db.goalDao().deleteAllFromContentProvider()
            }
            CODE_STEP_ALL -> {
                deleted = db.stepDao().deleteAllFromContentProvider()
            }
            CODE_TASK_ALL -> {
                deleted = db.taskDao().deleteAllFromContentProvider()
            }
            CODE_IDEA_ALL -> {
                deleted = db.ideaDao().deleteAllFromContentProvider()
            }
            CODE_KEY_ALL -> {
                deleted = db.keyDao().deleteAllFromContentProvider()
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri -> ${uriMatcher.match(uri)}")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return deleted
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        p2: String?,
        p3: Array<out String>?
    ): Int {
        Log.d("CPSA","upd")
        values?.let {
            val updated: Int
            when (uriMatcher.match(uri)) {
                CODE_GOAL_ITEM -> {
                    updated =
                        db.goalDao().updateFromContentProvider(goalEntityFromContentValues(it))
                }
                CODE_STEP_ITEM -> {
                    updated =
                        db.stepDao().updateFromContentProvider(stepEntityFromContentValues(it))
                }
                CODE_TASK_ITEM -> {
                    updated =
                        db.taskDao().updateFromContentProvider(taskEntityFromContentValues(it))
                }
                CODE_IDEA_ITEM -> {
                    updated =
                        db.ideaDao().updateFromContentProvider(ideaEntityFromContentValues(it))
                }
                CODE_KEY_ITEM -> {
                    updated =
                        db.keyDao().updateFromContentProvider(keyEntityFromContentValues(it))
                }
                else -> throw IllegalArgumentException("Unknown URI: $uri -> ${uriMatcher.match(uri)}")
            }
            context?.contentResolver?.notifyChange(uri, null)
            return updated
        }
        return 0
    }


}