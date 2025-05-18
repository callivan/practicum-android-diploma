package ru.practicum.android.diploma.data.dto

data class VacanciesRequestDto(
    val text: String,
    val page: Int? = null,
    val area: String? = null,
    val salary: Int? = null,
    val only_with_salary: Boolean = false,
    val professional_role: String? = null
) {
    fun toQueryMap(): Map<String, String?> {
        val queries = mutableMapOf<String, String?>()

        queries.put("text", text)
        queries.put("page", page?.toString())
        queries.put("area", area)
        queries.put("salary", salary?.toString())
        queries.put("only_with_salary", only_with_salary.toString())
        queries.put("professional_role", professional_role)

        return queries.filterValues { it != null }
    }
}
