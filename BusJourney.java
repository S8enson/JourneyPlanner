/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journeyplanner;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import journeyplanner.*;

/**
 *
 * @author Sam
 */
public class BusJourney {

    /**
     * @param args the command line arguments
     */
    private List<BusTrip> busList;

    public BusJourney() {
        busList = new LinkedList<>();
    }

    public BusJourney(List<BusTrip> list) {
        this();
        for (Iterator<BusTrip> iterator = list.iterator(); iterator.hasNext();) {
            BusTrip next = iterator.next();
            this.addBus(next);
            
        }
        
    }

    public boolean addBus(BusTrip bus) {
        if(getDestination() == null){
        busList.add(bus);
            return true;
        }
        else if (getDestination() == bus.getDepartLocation() && getDestinationTime().compareTo(bus.getDepartTime()) < 1 && !containsLocation(bus.getArrivalLocation())) {
            busList.add(bus);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeLastTrip() {
        if (busList.isEmpty()) {
            return false;
        }
        busList.remove(busList.size()-1);
        return true;
    }

    public boolean containsLocation(String location) {
        for (int i = 0; i < busList.size(); i++) {
            if (busList.get(i).getDepartLocation() == location /*|| busList.get(i).getArrivalLocation() == location*/) {
                return true;
            }
        }
        return false;
    }

    public String getOrigin() {
        if(busList.isEmpty()){
        return null;
        }
        else {
        return busList.get(0).getDepartLocation();
        }
    }

    public String getDestination() {
        if(busList.isEmpty()){
        return null;
        }
        else {
        return busList.get(busList.size()-1).getArrivalLocation();
        }
    }

    public LocalTime getOriginTime() {
        if(busList.isEmpty()){
        return null;
        }
        else {
        return busList.get(0).getDepartTime();
        }
    }

    public LocalTime getDestinationTime() {
        if(busList.isEmpty()){
        return null;
        }
        else {
        return busList.get(busList.size()-1).getArrivalTime();
        }
    }

    public BusJourney cloneJourney() {
        return new BusJourney(busList);
    }

    public int getNumberOfBusTrips() {
        return busList.size();
    }

    public double getTotalCost() {
        double cost = 0;
        for (int i = 0; i < busList.size(); i++) {
            cost += busList.get(i).getCost();
        }
        return cost;
    }

    @Override
    public String toString() {
        String s = "TOTAL COST: " + getTotalCost() + "!!!";
        for (int i = 0; i < busList.size(); i++) {
            s+= "\n>BUS: " + busList.get(i).getBusNumber() + " LEAVING " + busList.get(i).getDepartLocation() + " (" + busList.get(i).getDepartTime() + ") and ARRIVING " + busList.get(i).getArrivalLocation() + " (" + busList.get(i).getArrivalTime() + ") $" + busList.get(i).getCost();
        }
        return s;
    }
}
