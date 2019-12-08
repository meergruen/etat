
public class BillArrayAdapter extends ArrayAdapter {
	
	
	DateFormat dateFormat = SimpleDateFormat("dd/MM/yy");
	
	private final int mResource;
	
	public BillArrayAdapter(Context context, int resource, List objects) {
		super(context, objects);
		mresource = resource;
	}
	
	
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
		
       Bill bill = getItem(position);    

       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.BillPreview, parent, false);
       }

       // Lookup view for data population

       TextView payee = (TextView) convertView.findViewById(R.id.preview_payee);
       TextView total = (TextView) convertView.findViewById(R.id.preview_price);
       TextView date = (TextView) convertView.findViewById(R.id.preview_date);
       TextView productList = (TextView) convertView.findViewById(R.id.preview_items);

       // Populate the data into the template view using the data object
       payee.setText(bill.getPayee());
       total.setText(bill.getTotalString());
       date.setText(dateFormat.format(bill.getDate()));
       productList.setText(bill.getItemPreview());
       

       return convertView;

   }
}
