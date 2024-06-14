import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.google.android.material.calendar.CalendarDay
import com.google.android.material.calendar.DayViewDecorator
import com.google.android.material.calendar.DayViewFacade

class DotSpan(context: Context) : DayViewDecorator {

    private val drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.dot_marker)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return true
    }

    override fun decorate(view: DayViewFacade?) {
        if (view != null && drawable != null) {
            view.addSpan(object : DayViewDecorator {
                override fun shouldDecorate(day: CalendarDay): Boolean {
                    return true
                }

                override fun decorate(view: DayViewFacade) {
                    view.setSelectionDrawable(drawable)
                }
            })
        }
    }
}
