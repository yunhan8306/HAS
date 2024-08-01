package com.myStash.android.core.model

import java.time.YearMonth

sealed interface CalenderData {
    data class DayOfWeek(val name: String): CalenderData
    object Spacer: CalenderData
    data class Day(val day: String): CalenderData
    data class RecodedDay(
        val day: String,
        val imageUri: String,
    ): CalenderData
}

fun setCalender(currentYear: Int, currentMonth: Int): MutableList<CalenderData> {
    val calenderDataList: MutableList<CalenderData> = mutableListOf()
    val yearMonth = YearMonth.of(currentYear, currentMonth)

    // 요일 데이터
    calenderDataList.addAllDayOfWeek()

    // 공백 데이터
    calenderDataList.addAllSpacerDay(yearMonth)

    // 날짜 데이터
    val daysInMonth = yearMonth.lengthOfMonth()
    (1..daysInMonth).forEach { day ->
        calenderDataList.add(
            CalenderData.Day(
                day = day.toString()
            )
        )
    }

    return calenderDataList
}

fun MutableList<CalenderData>.addAllDayOfWeek() {
    val dayOfWeekList = mutableListOf(
        CalenderData.DayOfWeek("일"),
        CalenderData.DayOfWeek("월"),
        CalenderData.DayOfWeek("화"),
        CalenderData.DayOfWeek("수"),
        CalenderData.DayOfWeek("목"),
        CalenderData.DayOfWeek("금"),
        CalenderData.DayOfWeek("토"),
    )
    addAll(dayOfWeekList)
}

fun MutableList<CalenderData>.addAllSpacerDay(yearMonth: YearMonth) {
    val firstDayOfMonth = yearMonth.atDay(1).dayOfWeek.value % 7 // Sunday as 0
    repeat(firstDayOfMonth) {
        add(CalenderData.Spacer)
    }
}