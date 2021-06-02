package com.slesarenkoas.personalplanningapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.slesarenkoas.personalplanningapp.model.Task

@Database(
	entities = [Task::class],
	version = 4
)
@TypeConverters(DatabaseTypeConverters::class)
abstract class TaskDatabase : RoomDatabase() {
	abstract fun taskDAO(): TaskDAO

	companion object {
		@Volatile
		private var INSTANCE: TaskDatabase? = null

		private val MIGRATION_1_2 = object : Migration(1, 2) {
			override fun migrate(database: SupportSQLiteDatabase) {
				database.execSQL("ALTER TABLE task ADD COLUMN color INTEGER NOT NULL DEFAULT 0")
			}
		}

		private val MIGRATION_2_3 = object : Migration(2, 3) {
			override fun migrate(database: SupportSQLiteDatabase) {
				database.execSQL("ALTER TABLE task ADD COLUMN isCategory INTEGER NOT NULL DEFAULT 0")
			}
		}

		private val MIGRATION_3_4 = object : Migration(3, 4) {
			override fun migrate(database: SupportSQLiteDatabase) {
				database.execSQL("create table TaskRelation (id integer primary key, name text not null)")
				database.execSQL("insert into TaskRelation (name) values ('category')")
				database.execSQL("create table TaskToTask (task1Id integer not null, task2Id integer not null, taskRelationId integer not null)")
			}
		}

		fun getDatabase(
			context: Context
		): TaskDatabase {
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext,
					TaskDatabase::class.java,
					"task_database"
				)
					.addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
					.build()
				INSTANCE = instance
				// return instance
				instance
			}
		}
	}
}

//val MIGRATION_1_2 = object : Migration(1, 2) {
//	override fun migrate(database: SupportSQLiteDatabase) {
//		database.execSQL("ALTER TABLE tasks ADD COLUMN pub_year INTEGER")
//	}
//}
