import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

class CalendarViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<Map<LocalDate, List<String>>>(emptyMap())
    val tasks: StateFlow<Map<LocalDate, List<String>>> get() = _tasks

    fun addTask(date: LocalDate, task: String) {
        val currentTasks = _tasks.value[date].orEmpty() + task
        _tasks.value = _tasks.value + (date to currentTasks)
    }
}
