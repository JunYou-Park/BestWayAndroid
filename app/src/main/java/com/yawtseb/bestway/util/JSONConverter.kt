package com.yawtseb.bestway.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject

class JSONConverter {

    companion object{
        fun generateSingleDataJSON(type: String, data: Any): JSONObject{
            var json = JSONObject()
            json.put(type, data)

            val jsArray = JSONArray()
            jsArray.put(json)

            json = JSONObject()
            json.put("data", jsArray);

            return json
        }


        fun generateSingleDataJson(type: String, data: Any): JsonObject{
            val JSON = JSONObject()
            JSON.put(type, data)

            return Gson().fromJson(JSON.toString(), JsonObject::class.java)
        }
    }

}