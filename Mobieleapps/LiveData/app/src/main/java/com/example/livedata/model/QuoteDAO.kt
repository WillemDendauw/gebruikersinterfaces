package com.example.livedata.model

import android.content.Context
import android.util.Log
import com.example.livedata.R
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.nio.Buffer

class QuoteDAO(c: Context): ArrayList<Quote>() {
    init {
        Log.i("context", c.toString())
        var result: JSONObject? = null

        try {
            // read file into string builder
            val inputStream: InputStream = c.applicationContext.resources.openRawResource(R.raw.quotes)
            val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
            val builder: StringBuilder = StringBuilder()
            var line: String? = reader.readLine()

            while (line != null){
                builder.append(line).append("\n")
                line = reader.readLine()
            }

            // parse into JSONObject
            val resultStr = builder.toString()
            val tokener:JSONTokener = JSONTokener(resultStr)
            result = JSONObject(tokener)
            val a: JSONArray = result.getJSONArray("quotes")
            for(i in 0..a.length()){
                val quote:String = a.getJSONObject(i).get("quote").toString()
                val author:String = a.getJSONObject(i).get("author").toString()
                add(Quote(quote, author))
            }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}