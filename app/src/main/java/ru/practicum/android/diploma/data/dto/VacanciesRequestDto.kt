package ru.practicum.android.diploma.data.dto

data class VacanciesRequestDto(
    val text: String,
    val page: Int? = null,
    val area: List<String>? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
    val industry: List<String>? = null
)

fun VacanciesRequestDto.toQueryMap(): Map<String, String?> {
    val queries = mutableMapOf<String, String?>()

    queries.put("text", text)
    queries.put("page", page?.toString())
    queries.put("salary", salary?.toString())
    queries.put("only_with_salary", onlyWithSalary.toString())

    area?.forEach { queries.put("area", it) }
    industry?.forEach { queries.put("industry", it) }

    return queries.filterValues { it != null }
}
