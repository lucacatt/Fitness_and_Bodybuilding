import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.fitnessbodybuilding.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarAdapter(
    private val context: Context,
    private val calendar: Calendar,
    private val highlightedDays: List<Int> = emptyList()
) : BaseAdapter() {

    private val dateFormat = SimpleDateFormat("d", Locale.getDefault())

    override fun getCount(): Int {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + getFirstDayOffset()
    }

    override fun getItem(position: Int): Any? {
        return null // Not needed in this case
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_calendar_day, parent, false)
        val dayTextView = view.findViewById<TextView>(R.id.dayTextView)

        val dayOfMonth = position + 1 - getFirstDayOffset()
        if (dayOfMonth in 1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            dayTextView.text = dateFormat.format(getDateForPosition(position))
            if (dayOfMonth in highlightedDays) {
                // Highlight the day
                dayTextView.setBackgroundColor(Color.YELLOW) // Or any color you prefer
            } else {
                // Reset background color if not highlighted
                dayTextView.setBackgroundColor(Color.TRANSPARENT)
            }
        } else {
            dayTextView.text = "" // Empty for days outside the current month
        }

        return view
    }

    private fun getFirstDayOffset(): Int {
        val firstDayOfMonth = calendar.clone() as Calendar
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1)
        return firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 1
    }

    private fun getDateForPosition(position: Int): Date {
        val dayOfMonth = position + 1 - getFirstDayOffset()
        val date = calendar.clone() as Calendar
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        return date.time
    }
}