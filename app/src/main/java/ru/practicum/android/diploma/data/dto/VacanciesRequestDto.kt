package ru.practicum.android.diploma.data.dto

data class VacanciesRequestDto(
    val text: String,
    val page: Int? = null,
    val area: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
    val professionalRole: String? = null
)

fun VacanciesRequestDto.toQueryMap(): Map<String, String?> {
    val queries = mutableMapOf<String, String?>()

    queries.put("text", text)
    queries.put("page", page?.toString())
    queries.put("area", area)
    queries.put("salary", salary?.toString())
    queries.put("only_with_salary", onlyWithSalary.toString())
    queries.put("professional_role", professionalRole)

    return queries.filterValues { it != null }
}
