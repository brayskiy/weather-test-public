package library.core.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.lang.reflect.Type

fun getDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        return null
    }
    return jsonString
}

inline fun <reified T> getJsonFromString(jsonString: String, typeToken: Type) : T {
    return Gson().fromJson(jsonString, typeToken)
}
