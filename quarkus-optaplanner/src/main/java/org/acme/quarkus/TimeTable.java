package org.acme.quarkus;

import java.util.List;

import org.acme.quarkus.domain.Lesson;
import org.acme.quarkus.domain.Room;
import org.acme.quarkus.domain.Timeslot;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class TimeTable {
  @ProblemFactCollectionProperty
  @ValueRangeProvider(id = "timeslotRange")
  private List<Timeslot> timeslotList; 
  
  @ProblemFactCollectionProperty
  @ValueRangeProvider(id = "roomRange")
  private List<Room> roomList;

  @PlanningEntityCollectionProperty
  private List<Lesson> lessonList;

  @PlanningScore
  private HardSoftScore score;

  public TimeTable(){

  }

  /**
   * @param timeslotList
   * @param roomList
   * @param lessonList
   * @param score
   */
  public TimeTable(List<Timeslot> timeslotList, List<Room> roomList, 
    List<Lesson> lessonList) {
    this.timeslotList = timeslotList;
    this.roomList = roomList;
    this.lessonList = lessonList;
  }

  /**
   * @return the timeslotList
   */
  public List<Timeslot> getTimeslotList() {
    return timeslotList;
  }

  /**
   * @return the roomList
   */
  public List<Room> getRoomList() {
    return roomList;
  }


  /**
   * @return the lessonList
   */
  public List<Lesson> getLessonList() {
    return lessonList;
  }


  /**
   * @return the score
   */
  public HardSoftScore getScore() {
    return score;
  }



}