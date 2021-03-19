package com.volynkun.cft_focus_start.data

import com.volynkun.cft_focus_start.networking.Networking
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


object Repository {

    private var currencies = mutableListOf<Currency>()
    private var json: String? = null
    private var date: String? = null

    fun updateJson(savedInstance: String?) {
        json = savedInstance
    }

    fun getCurrencies(): MutableList<Currency> {
        return currencies
    }

    fun getMyJson(): String? {
        return json
    }

    private fun parse(json: String?) {
        val reader = JSONObject(json!!)
        date = reader.getString("Timestamp")
        val currency = reader.getString("Valute")
        val currencyJson = JSONObject(currency)
        val iteratorObj: Iterator<*> = currencyJson.keys()
        val keys = mutableListOf<String>()
        while (iteratorObj.hasNext()) {
            val key = iteratorObj.next() as String
            keys.add(key)
        }
        currencies = mutableListOf()
        keys.map { key -> currencyJson.getJSONObject(key) }
            .map { currencyJsonObject ->
                val id = currencyJsonObject.getString("ID")
                val num = currencyJsonObject.getInt("NumCode")
                val char = currencyJsonObject.getString("CharCode")
                val nom = currencyJsonObject.getInt("Nominal")
                val name = currencyJsonObject.getString("Name")
                val value = currencyJsonObject.getDouble("Value")
                val prev = currencyJsonObject.getDouble("Previous")
                val currencyToList = Currency(id, num, char, nom, name, value, prev)
                currencies.add(currencyToList)
            }
    }

    fun searchJson(
        isManual: Boolean,
        onComplete: (MutableList<Currency>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        if ((json == null) || isManual) {
            download(onComplete, onError)
        } else {
            parse(json)
            val cutDateString = date?.substring(0, 10)
            val currentDate: LocalDate = Calendar.getInstance().time.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate()
            val date = LocalDate.parse(cutDateString)
            if (currentDate > date) {
                download(onComplete, onError)
            } else {
                onComplete(currencies)
            }
        }
    }

    private fun download(
        onComplete: (MutableList<Currency>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Networking.api.download("daily_json.js").enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        json = response.body()
                        parse(json)
                        onComplete(currencies)
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    parse(json)
                    onComplete(currencies)
                }
            }
        )
    }
}