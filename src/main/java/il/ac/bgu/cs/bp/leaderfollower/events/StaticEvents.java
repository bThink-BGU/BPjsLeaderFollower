package il.ac.bgu.cs.bp.leaderfollower.events;

import il.ac.bgu.cs.bp.bpjs.model.BEvent;

@SuppressWarnings({"serial"})
public class StaticEvents {
    public static final BEvent TICK = new BEvent("Tick");
    public static final BEvent START_CONTROL = new BEvent("StartControl");
    public static final BEvent TURN_LEFT = new BEvent("TurnLeft");
    public static final BEvent TURN_RIGHT = new BEvent("TurnRight");
    public static final BEvent BREAK_ON = new BEvent("BrakeOn");
    public static final BEvent ORIENTATION_OK = new BEvent("OrientationOK");
    public static final BEvent GO_TO_TARGET = new BEvent("GoToTarget");
}
