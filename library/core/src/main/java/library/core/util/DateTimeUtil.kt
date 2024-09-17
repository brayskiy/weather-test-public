package library.core.util

import java.text.SimpleDateFormat
import java.util.Date

fun epochToDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("EEEE, MMMM dd, yyyy")
    val netDate = Date(timestamp * 1000)
    return sdf.format(netDate)
}
