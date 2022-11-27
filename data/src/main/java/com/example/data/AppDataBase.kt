package com.example.data.sources.group.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.sources.room.GroupDao
import com.example.data.sources.room.UserEntity
import com.example.data.sources.room.UsersDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    version = 1,
    entities = [
        GroupEntity::class,
        UserEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun usersDao(): UsersDao

    /*private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { db ->
                scope.launch {
                    var wordDao = db.groupDao()

                    // Delete all content here.
                    //wordDao.deleteAll()

                    // Add sample words.
                    //var word = Word("Hello")
                    //wordDao.insert(word)
                    //word = Word("World!")
                    //wordDao.insert(word)
                }
            }
        }
    }*/

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "myapp_database"
                )
                    //.addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}