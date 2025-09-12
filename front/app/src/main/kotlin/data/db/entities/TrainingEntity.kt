package com.application.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.application.domain.models.Training
import com.application.domain.models.Exercise
import com.application.domain.models.SportEventStatus
import com.application.domain.models.AttendanceInfo
import com.application.domain.models.Attendance
import com.application.domain.models.FutureAttendance
import com.application.domain.models.PastAttendance
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

@Serializable
data class ExerciseEntity(
    val id: String,
    val title: String,
    val durationInMinutes: Int,
)

@Serializable
data class AttendanceInfoEntity(
    val futureAttendance: Map<String, Int> = emptyMap(),
    val pastAttendance: Map<String, Int> = emptyMap(),
)

class TrainingConverters {
    private val json = Json

    @TypeConverter
    fun fromExerciseList(exercises: List<ExerciseEntity>): String {
        return json.encodeToString(exercises)
    }

    @TypeConverter
    fun toExerciseList(exercisesString: String): List<ExerciseEntity> {
        return json.decodeFromString(exercisesString)
    }

    @TypeConverter
    fun fromPhotoList(photos: List<String>): String {
        return json.encodeToString(photos)
    }

    @TypeConverter
    fun toPhotoList(photosString: String): List<String> {
        return json.decodeFromString(photosString)
    }

    @TypeConverter
    fun fromAttendanceInfo(attendanceInfo: AttendanceInfoEntity): String {
        return json.encodeToString(attendanceInfo)
    }

    @TypeConverter
    fun toAttendanceInfo(attendanceInfoString: String): AttendanceInfoEntity {
        return json.decodeFromString(attendanceInfoString)
    }

    @TypeConverter
    fun fromLocalTime(time: LocalTime): String {
        return time.format(DateTimeFormatter.ISO_LOCAL_TIME)
    }

    @TypeConverter
    fun toLocalTime(timeString: String): LocalTime {
        return LocalTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_TIME)
    }
}

@Entity(tableName = "trainings")
@TypeConverters(TrainingConverters::class)
data class TrainingEntity(
    @PrimaryKey
    val id: Int,
    val status: String, // Store as string key
    val startDateTime: LocalTime,
    val endDateTime: LocalTime,
    val type: String,
    val note: String,
    val place: String,
    val title: String,
    val photos: List<String>,
    val exercises: List<ExerciseEntity>,
    val plannedAttendance: AttendanceInfoEntity,
    val realAttendance: AttendanceInfoEntity,
)

// Extension functions to convert between Entity and Domain models
fun ExerciseEntity.toDomainModel(): Exercise {
    return Exercise(
        id = id,
        title = title,
        durationInMinutes = durationInMinutes,
    )
}

fun Exercise.toEntity(): ExerciseEntity {
    return ExerciseEntity(
        id = id,
        title = title,
        durationInMinutes = durationInMinutes,
    )
}

fun AttendanceInfoEntity.toDomainModel(): AttendanceInfo {
    val attendanceMap = mutableMapOf<Attendance, Int>()
    
    // Add future attendance entries
    futureAttendance.forEach { (key, value) ->
        FutureAttendance.values().find { it.key == key }?.let { attendance ->
            attendanceMap[attendance] = value
        }
    }
    
    // Add past attendance entries
    pastAttendance.forEach { (key, value) ->
        PastAttendance.values().find { it.key == key }?.let { attendance ->
            attendanceMap[attendance] = value
        }
    }
    
    return AttendanceInfo(attendanceMap)
}

fun AttendanceInfo.toEntity(): AttendanceInfoEntity {
    val futureMap = mutableMapOf<String, Int>()
    val pastMap = mutableMapOf<String, Int>()
    
    info.forEach { (attendance, count) ->
        when (attendance) {
            is FutureAttendance -> futureMap[attendance.key] = count
            is PastAttendance -> pastMap[attendance.key] = count
        }
    }
    
    return AttendanceInfoEntity(
        futureAttendance = futureMap,
        pastAttendance = pastMap,
    )
}

fun TrainingEntity.toDomainModel(): Training {
    return Training(
        id = id,
        status = SportEventStatus.values().first { it.key == status },
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        type = type,
        note = note,
        place = place,
        title = title,
        photos = photos,
        exercises = exercises.map { it.toDomainModel() },
        plannedAttendance = plannedAttendance.toDomainModel(),
        realAttendance = realAttendance.toDomainModel(),
    )
}

fun Training.toEntity(): TrainingEntity {
    return TrainingEntity(
        id = id,
        status = status.key,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        type = type,
        note = note,
        place = place,
        title = title,
        photos = photos,
        exercises = exercises.map { it.toEntity() },
        plannedAttendance = plannedAttendance.toEntity(),
        realAttendance = realAttendance.toEntity(),
    )
}
