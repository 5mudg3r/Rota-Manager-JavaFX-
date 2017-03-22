package me.smudja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This enum manages all the shifts in the current rota configuration.
 * Only 1 instance of this enum ever exists and is accessed through {@code INSTANCE}
 * 
 * @author smithl
 * @see    me.smudja.Shift
 */
enum ShiftManager {
	
	INSTANCE;

	/**
	 * A String array storing the headers for the current shift configuration.
	 */
	private String[] headers;
	
	/**
	 * An array list storing all the current shifts
	 */
	private ArrayList<Shift> shifts;
	
	/**
	 * Constructor for the enum.
	 * This is only called when the enum is first called in {@link me.smudja.RotaManager#init() RotaManager}.
	 */
	private ShiftManager() {
		shifts = new ArrayList<Shift>();
		headers = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	}

	/**
	 * This method loads an array list of shifts into {@code shifts} from a file specified from the parameter.
	 * Note, in general this should load from the same file as {@link me.smudja.ShiftManager#save(String) save()} saves to.
	 * It handles any exceptions by printing a message to console.
	 * 
	 * @param fromFile	The file to load the shifts from
	 */	
	@SuppressWarnings("unchecked")
	public void load(File fromFile) {
		try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(fromFile))) {
			Object[] objects = (Object[]) inStream.readObject();
			this.shifts = (ArrayList<Shift>) objects[0];
			this.headers = (String[]) objects[1];
		}
		catch (Exception exc) {
			System.out.println("Unable to load from file: " + fromFile);
		}
	}
	
	/**
	 * This method saves {@code shifts} to the file specified in the parameter.
	 * Note, in general this should save to the same file as {@link me.smudja.ShiftManager#load(String) load()} loads from.
	 * It handles any exceptions by printing a message to console.
	 * 
	 * @param saveFile	The file to save to
	 */
	public void save(File saveFile) {
		try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(saveFile))) {
			outStream.writeObject(new Object[]{shifts, headers});
		}
		catch (IOException exc) {
			System.out.println("Unable to save to file: " + saveFile);
		}
	}
	
	/**
	 * This method empties the {@code shifts} array list
	 */
	public void purge() {
		shifts.clear();
		headers = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	}
	
	/**
	 * Getter for headers
	 * 
	 * @return headers
	 */
	public String[] getHeaders() {
		return headers;
	}
	
	/**
	 * Setter for headers
	 * 
	 * @param headers the headers to set
	 */
	public void setHeaders(String[] headers) {
		this.headers = headers;
	}
	
	/**
	 * This method adds the shift specified in the parameter to {@code shifts}, if it is not already present.
	 * 
	 * @param shift	The shift to add
	 * 
	 * @see me.smudja.Shift
	 */
	public void add(Shift shift) {
		if(!shifts.contains(shift)) shifts.add(shift);
	}
	
	/**
	 * This method removes the shift specified in the parameter from {@code shifts}, if it is present.
	 * 
	 * @param shift	The shift to remove
	 * 
	 * @see me.smudja.Shift
	 */
	public void remove(Shift shift) {
		if(shifts.contains(shift)) shifts.remove(shift);
	}
	
	/**
	 * This methods returns all shifts in {@code shifts} as a {@link me.smudja.Shift Shift} array.
	 * 
	 * @return All shifts as an array.
	 * 
	 * @see	   me.smudja.Shift
	 */
	public Shift[] getShifts() {
		Shift[] shiftsArr = new Shift[shifts.size()];
		shiftsArr = shifts.toArray(shiftsArr);
		return shiftsArr;
	}
	
	/**
	 * This method checks to see if any shifts clash with the specified shift and returns any that do.
	 * It returns null if there are no clashes.
	 * 
	 * @param shift	The shift to check for clashes against
	 * @return		A shift in {@code shifts} which clashes or {@code null}
	 * 
	 * @see			me.smudja.Shift
	 */
	public Shift clashes(Shift shift) {
		for(Shift s : shifts) {
			if(shift.getDay().equals(s.getDay()) && shift.getPeriod().equals(s.getPeriod())) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * This method returns an array of all the ingredients required for the current rota configuration.
	 * It does not give duplicate ingredients i.e. if a meal appears twice it will only add that meals ingredients once
	 * 
	 * @return An array of the required ingredients for the current rota configuration.
	 */
	public String[] getWeeksIngredients() {
		ArrayList<String> aryIngreds = new ArrayList<String>();
		for(Shift shift: shifts) {
			for(String ingred: shift.getIngredients()) {
				if(!aryIngreds.contains(ingred)) aryIngreds.add(ingred);
			}
		}
		String[] ingreds = new String[aryIngreds.size()];
		return ingreds = aryIngreds.toArray(ingreds);
	}
	
}
