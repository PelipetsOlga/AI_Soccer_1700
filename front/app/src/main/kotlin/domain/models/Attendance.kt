package com.application.domain.models

import com.application.R
import com.application.domain.base.EnumWithKey

interface Attendance {
}

enum class FutureAttendance(
    override val key: String,
    val titleId: Int,
) : EnumWithKey, Attendance {
    Going(key = "Going", titleId = R.string.attendance_future_going),
    NotGoing(key = "NotGoing", titleId = R.string.attendance_future_not),
    Maybe(key = "Maybe", titleId = R.string.attendance_future_maybe),
}

enum class PastAttendance(
    override val key: String,
    val titleId: Int,
) : EnumWithKey, Attendance {
    Present(key = "Present", titleId = R.string.attendance_past_present),
    Late(key = "Late", titleId = R.string.attendance_past_late),
    Absent(key = "Absent", titleId = R.string.attendance_past_absent),
}

data class AttendanceInfo(
    val info: Map<Attendance, Int>
)