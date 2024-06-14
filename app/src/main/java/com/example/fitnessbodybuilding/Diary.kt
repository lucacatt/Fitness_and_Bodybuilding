import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbodybuilding.Allenamento
import com.example.fitnessbodybuilding.DataManagement
import com.example.fitnessbodybuilding.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

class Diary : Fragment() {

    private lateinit var currentMonthText: TextView
    private lateinit var previousMonthArrow: ImageView
    private lateinit var nextMonthArrow: ImageView
    private lateinit var calendarGridView: GridView
    private lateinit var recyclerViewWorkouts: RecyclerView
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    private var highlightedDays: MutableList<Int> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary, container, false)

        currentMonthText = view.findViewById(R.id.currentMonthText)
        previousMonthArrow = view.findViewById(R.id.previousMonthArrow)
        nextMonthArrow = view.findViewById(R.id.nextMonthArrow)
        calendarGridView = view.findViewById(R.id.calendarGridView)
        recyclerViewWorkouts = view.findViewById(R.id.recyclerViewWorkouts)

        updateMonthDisplay()

        previousMonthArrow.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateMonthDisplay()
        }

        nextMonthArrow.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateMonthDisplay()
        }

        recyclerViewWorkouts.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun updateMonthDisplay() {
        highlightedDays = DataManagement.getInstance().fillHighlightedDays(calendar.get(Calendar.MONTH))
        currentMonthText.text = dateFormat.format(calendar.time)
        calendarGridView.adapter = CalendarAdapter(requireContext(), calendar, highlightedDays)
        val trainingsList: MutableList<Allenamento> = DataManagement.getInstance().getAllenamenti(calendar.get(Calendar.MONTH))
        recyclerViewWorkouts.adapter = WorkoutAdapter(trainingsList)
    }

}
