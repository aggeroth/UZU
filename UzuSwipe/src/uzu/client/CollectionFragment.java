package uzu.client;

import java.util.ArrayList;
import com.example.uzuswipe.R;
import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This class is responsible for the collection tab which displays a list of all
 * Uzu items that have been picked up
 * 
 * @author Merisha & Nem
 * 
 */
public class CollectionFragment extends Fragment {

	/**
	 * Contains all Uzu items to be displayed
	 */
	static ArrayList<UzuFragment> uzuFrag = new ArrayList<UzuFragment>();

	/**
	 * Getter for Uzu Fragments
	 * @return UzuFragment[]
	 */
	static public UzuFragment[] getUzuFragements() {
		return uzuFrag.toArray(new UzuFragment[0]);
	}

	/**
	 * Method that constructs the view for the collection page.
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ArrayList<UzuFragment> uzuFragments = new ArrayList<UzuFragment>();
		
		if (container == null) {
			return null;
		}
		
		final Activity activity = getActivity();
		DatabaseHandler db = new DatabaseHandler(activity);
		Uzu[] uzus = db.getAllUzus();

		//Add Uzu items to Uzu ArrayList
		for (int i = 0; i < uzus.length; i++) {
			UzuFragment uz = UzuFragment.newInstance(uzus[i].getLongitude(),
					uzus[i].getLatitude(), uzus[i].getSubject(),
					uzus[i].getMessage(), uzus[i].getImage(),
					uzus[i].getLife(), uzus[i].getCategoryID());
			uz.setSubject(uzus[i].getSubject());
			uz.setUzuID(uzus[i].getUzuID());
			uz.setImage(uzus[i].getImage());
			uzuFragments.add(uz);
		}

		View view = (LinearLayout) inflater.inflate(
				R.layout.fragment_collection, container, false);

		ListView listView = (ListView) view.findViewById(R.id.itemCollection);

		ArrayAdapter<UzuFragment> itemAdapter = new ArrayAdapter<UzuFragment>(
				activity, android.R.layout.simple_list_item_multiple_choice,
				uzuFragments.toArray(new UzuFragment[0]));

		listView.setAdapter(itemAdapter);

		// Specifying what happens when one item is clicked.
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(activity, ItemActivity.class);

				intent.putExtra("position", position);
				startActivity(intent);
			}
		});
		
		uzuFrag = uzuFragments;
		return view;
	}
}
