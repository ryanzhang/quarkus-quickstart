package org.acme.quarkus.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Lesson {

  @PlanningId
  private Long id;

  private String subject;
  private String teacher;
  private String studentGroup;

  @PlanningVariable(valueRangeProviderRefs = "timeslotRange")
  private Timeslot timeslot;

  @PlanningVariable(valueRangeProviderRefs = "roomRange")
  private Room room;

  /**
   * 
   */
  public Lesson() {
  }

  /**
   * @param id
   * @param subject
   * @param teacher
   * @param studentGroup
   * @param timeslot
   * @param room
   */
  public Lesson(Long id, String subject, String teacher, String studentGroup) {
    this.id = id;
    this.subject = subject;
    this.teacher = teacher;
    this.studentGroup = studentGroup;

  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * @param subject the subject to set
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * @return the teacher
   */
  public String getTeacher() {
    return teacher;
  }

  /**
   * @param teacher the teacher to set
   */
  public void setTeacher(String teacher) {
    this.teacher = teacher;
  }

  /**
   * @return the studentGroup
   */
  public String getStudentGroup() {
    return studentGroup;
  }

  /**
   * @param studentGroup the studentGroup to set
   */
  public void setStudentGroup(String studentGroup) {
    this.studentGroup = studentGroup;
  }

  /**
   * @return the timeslot
   */
  public Timeslot getTimeslot() {
    return timeslot;
  }

  /**
   * @param timeslot the timeslot to set
   */
  public void setTimeslot(Timeslot timeslot) {
    this.timeslot = timeslot;
  }

  /**
   * @return the room
   */
  public Room getRoom() {
    return room;
  }

  /**
   * @param room the room to set
   */
  public void setRoom(Room room) {
    this.room = room;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */

  @Override
  public String toString() {
    return "Lesson [id=" + id + ", subject=" + subject + "]";
  }

  
}