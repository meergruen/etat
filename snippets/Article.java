public class Article {

	static private int count = 0;
	
	String name;
	double price;
	String comment;
	Set<String> tags;
	
	Bill bill;
	
	public Article(JSONObject obj, Bill bill) {
		articleNumber = count;
		++count
		
		name = obj.getString("name");
		price = obj.getDouble("price");
		comment = obj.getString("comment");
		JsonArray tagArray = obj.getArray("tags");
		
		bills = bill;
		
		for ( String tag: tagArray ) {
			tags.add(tag);
		}
	}
	
	public Article() {
		articleNumber = count;
		++count
		
		name = "";
		price = 0;
		comment = "";
		tags = Set<String>();
		
	}
		
	String getName()          { return this.name;          }
	double getPrice()         { return this.price;         }
	String getComment()       { return this.comment; }
	Set<String> getItems()    { return this.tags;         }
	
	setDate(Date name)         { this.name = name;         }
	setPrice(double price)     { this.price = price;       }
	setComment(String comment) { this.comment = comment;   }
	setItems(Set<String> tags) { this.tags = tags;         }
		
				
	public toString() {
		
		String tagsStr = '[';
		for (String tag: tags) {
			tagsStr += '"' + tag + '",';
		}
		tagsStr[tagsStr.length-2] = ']';
		
		return '{"name":"' + name + 
		     '", "price":' + price +
		      ', "comment":"' + comment + 
		     '", "tags":[' + tagsStr + ']}';
	}

}
