package com.example.todo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todo.data.dao.CategoryDao
import com.example.todo.data.dao.StageDao
import com.example.todo.data.dao.TaskDao
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.data.model.TaskEntity
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [TaskEntity::class, Stage::class, Category::class],
    version = 1
)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
    abstract fun getStageDao(): StageDao
    abstract fun getCategoryDao(): CategoryDao

    companion object{

        const val DATABASE_NAME = "todo_database"
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getInstance(context: Context): ToDoDatabase = INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder<ToDoDatabase>(context.applicationContext, DATABASE_NAME)
                    .addCallback(
                        object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)

                                CoroutineScope(Dispatchers.IO + CoroutineName(DATABASE_NAME + "coroutine")).launch {
                                    db.execSQL(Category.INITIALIZER_SQL)
                                    db.execSQL(Stage.INITIALIZER_SQL)
                                }
                            }

                            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                                super.onDestructiveMigration(db)

                                CoroutineScope(Dispatchers.IO + CoroutineName(DATABASE_NAME + "coroutine")).launch {
                                    db.execSQL(Category.INITIALIZER_SQL)
                                    db.execSQL(Stage.INITIALIZER_SQL)
                                }
                            }
                        }
                    )
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also {
                        INSTANCE = it
                        it.openHelper.writableDatabase
                    }
            }
    }
}