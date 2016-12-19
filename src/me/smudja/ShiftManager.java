package me.smudja;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

enum ShiftManager {
	
	INSTANCE;
	
	private ArrayList<Shift> shifts;
	
	private ShiftManager() {
		shifts = new ArrayList<Shift>();
	}

	@SuppressWarnings("unchecked")
	public void load(String fromFile) {
		try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(fromFile))) {
			this.shifts = (ArrayList<Shift>) inStream.readObject();
		}
		catch (Exception exc) {
			System.out.println("Unable to load from file: " + fromFile);
		}
	}
	
	public void save(String toFile) {
		try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(toFile))) {
			outStream.writeObject(shifts);
		}
		catch (IOException exc) {
			System.out.println("Unable to save to file: " + toFile);
		}
	}
	
	public void purge() {
		shifts.clear();
	}
	
	public void add(Shift shift) {
		if(!shifts.contains(shift)) shifts.add(shift);
	}
	
	public void remove(Shift shift) {
		if(shifts.contains(shift)) shifts.remove(shift);
	}
	
	public Shift[] getShifts() {
		Shift[] shiftsArr = new Shift[shifts.size()];
		shiftsArr = shifts.toArray(shiftsArr);
		return shiftsArr;
	}
	
	public Shift clashes(Shift shift) {
		for(Shift s : shifts) {
			if(shift.getDay().equals(s.getDay()) && shift.getPeriod().equals(s.getPeriod())) {
				return s;
			}
		}
		return null;
	}

	public void printToConsole() {
		for(Shift s : shifts) {
			s.printToConsole();
			System.out.println();
		}
	}
	
}
