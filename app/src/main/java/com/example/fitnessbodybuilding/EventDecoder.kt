
import android.content.Context
import com.google.android.material.calendar.CalendarDay
import com.google.android.material.calendar.DayViewDecorator
import com.google.android.material.calendar.DayViewFacade
import java.time.LocalDate

class EventDecorator(context: Context, private val dates: List<LocalDate>) : DayViewDecorator {

    private val context = context.applicationContext

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day.date)
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(context))
    }
}
