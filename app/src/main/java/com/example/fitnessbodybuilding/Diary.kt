import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitnessbodybuilding.R
import com.google.android.material.calendar.MaterialCalendarView
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.YearMonth

class Diary : Fragment() {

    private lateinit var calendarView: MaterialCalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_diary, container, false)

        // Inizializza il calendario
        calendarView = rootView.findViewById(R.id.calendarView)

        // Esempio di allenamenti (da sostituire con i tuoi dati reali)
        val allenamenti = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            listOf(
                LocalDate.parse("2024-01-13"),
                LocalDate.parse("2024-06-13")
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        // Ottieni il mese corrente
        val currentMonth = YearMonth.now()

        // Configura il calendario
        calendarView.setOnDateChangedListener { widget, date, selected ->
            // Azioni quando viene selezionata una data
            Snackbar.make(rootView, "Data selezionata: $date", Snackbar.LENGTH_SHORT).show()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            calendarView.state().edit()
                .setMinimumDate(currentMonth.atDay(1))
                .setMaximumDate(currentMonth.atEndOfMonth())
                .commit()
        }

        calendarView.addDecorators(
            // Decoratore per evidenziare i giorni con allenamenti
            EventDecorator(requireContext(), allenamenti)
        )

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Diary().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
