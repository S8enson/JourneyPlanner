/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journeyplanner;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Sam
 */
public class JourneyPlanner {

    private Map<String, Set<BusTrip>> locationMap;

    public JourneyPlanner() {
        locationMap = new ConcurrentHashMap<>();
    }

    public boolean add(BusTrip bus) {
        if (locationMap.containsKey(bus.getDepartLocation())) {
            return locationMap.get(bus.getDepartLocation()).add(bus);
        } else {
            Set set = new HashSet();
            set.add(bus);
            locationMap.put(bus.getDepartLocation(), set);
            return true;
        }
    }

    public List<BusJourney> getPossibleJourneys(String startLocation, LocalTime startTime, String endLocation, LocalTime endTime) {
        //give all bus trips that leave
        List<BusJourney> journeyList = new ArrayList<>();
        BusJourney currentJourney = new BusJourney();

        findPaths(startLocation, startTime, endLocation, endTime, currentJourney, journeyList);
        return journeyList;
    }

    private void findPaths(String currentLocation, LocalTime currentTime, String endLocation, LocalTime endTime, BusJourney currentJourney, List<BusJourney> journeyList) {
        if(currentLocation == endLocation){
        BusJourney cloneJourney = currentJourney.cloneJourney();
        journeyList.add(cloneJourney);
        return;
        }
        Set tempSet = locationMap.get(currentLocation);
        BusTrip next;
        for (Iterator iterator = tempSet.iterator(); iterator.hasNext();) {
            next = (BusTrip) iterator.next();
            if (next.getDepartTime().compareTo(currentTime) >= 0 && next.getArrivalTime().compareTo(endTime) <= 0) {
                if (currentJourney.addBus(next)) {
                    findPaths(next.getArrivalLocation(), next.getArrivalTime(), endLocation, endTime, currentJourney, journeyList);
                    currentJourney.removeLastTrip();
                }
            }
            

        }

        
    }
}
