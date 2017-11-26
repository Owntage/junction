package ru.nobird.junction.util

/**
 * Created by lytr777 on 25/11/2017.
 */
object FormatHelper {
    fun formatContractToJson(serial: String, uri: String): String {
        val sb = StringBuilder()
        return sb.append("{\"Uri\": \"").append(serial).append("/").append(uri).append("\"}").toString()
    }

    fun pathFormatHelper(serial: String, path: String): String {
        val sb = StringBuilder()
        return sb.append(SCHEME_PREFIX).append(serial).append("/").append(path).toString()
    }

    val SCHEME_PREFIX = "suunto://"
    val URI_EVENT_LISTENER = "suunto://MDS/EventListener"
}