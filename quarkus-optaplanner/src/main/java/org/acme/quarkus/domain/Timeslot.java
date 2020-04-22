package org.acme.quarkus.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Timeslot {
  private DayOfWeek dayOfWeek;
  private LocalTime startTime;
  private LocalTime endTime;

  public Timeslot(){

  }

  /**
   * @param dayOfWeek
   * @param startTime
   * @param endTime
   */
  public Timeslot(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
    this.dayOfWeek = dayOfWeek;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   * @return the dayOfWeek
   */
  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  /**
   * @param dayOfWeek the dayOfWeek to set
   */
  public void setDayOfWeek(DayOfWeek dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  /**
   * @return the startTime
   */
  public LocalTime getStartTime() {
    return startTime;
  }

  /**
   * @param startTime the startTime to set
   */
  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  /**
   * @return the endTime
   */
  public LocalTime getEndTime() {
    return endTime;
  }

  /**
   * @param endTime the endTime to set
   */
  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */

  @Override
  public String toString() {
    return "Timeslot [dayOfWeek=" + dayOfWeek + ", startTime=" + startTime + "]";
  }

}