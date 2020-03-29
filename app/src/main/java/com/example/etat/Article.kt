package com.example.etat

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Article {
    @JvmField
	var name: String = ""
    @JvmField
    var price = 0.0
    @JvmField
    var comment: String = ""

    var items: HashSet<String> = HashSet()
    var articleNumber: Int
    var bill: Bill = Bill()

    constructor(obj: JSONObject, bill: Bill) {
        articleNumber = count
        ++count

        try {
            name = obj.getString("name")
            price = obj.getDouble("price")
            comment = obj.getString("comment")
            val tagArray = obj.getJSONArray("tags")
            for (i in 0 until tagArray.length()) {
                items.add(tagArray.getString(i))
            }
        } catch (e: JSONException) {
            Log.i("Article", "A field is missing in object")
        }
        this.bill = bill
    }

    constructor() {
        articleNumber = count
        ++count
    }

    override fun toString(): String {
        var tagsStr = ""
        for (tag in items) {
            tagsStr += "\"$tag\","
        }
        tagsStr = tagsStr.substring(0, tagsStr.length - 1)
        return "{\"name\":\"" + name +
                "\", \"price\":" + price +
                ", \"comment\":\"" + comment +
                "\", \"tags\":[" + tagsStr + "]}"
    }

    companion object {
        private var count = 0
    }

}