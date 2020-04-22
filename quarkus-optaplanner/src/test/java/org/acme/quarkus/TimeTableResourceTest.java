package org.acme.quarkus;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.acme.quarkus.domain.Lesson;
import org.acme.quarkus.domain.Room;
import org.acme.quarkus.domain.Timeslot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TimeTableResourceTest {

  @Inject
  TimeTableResource timeTableResource;

  @Test
  @Timeout(600_000)
  public void solve(){
    TimeTable problem = generateProblem();
    TimeTable solution = timeTableResource.solve(problem);
    assertFalse(solution.getLessonList().isEmpty());
    for(Lesson lesson: solution.getLessonList()){
      assertNotNull(lesson.getTimeslot());
      assertNotNull(lesson.getRoom());
    }
    assertTrue(solution.getScore().isFeasible());
  }

  private TimeTable generateProblem() {
    List<Timeslot> timeslotList = new ArrayList<Timeslot>(){{
      add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8,30), LocalTime.of(9,30)));
      add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9,30), LocalTime.of(10,30)));
      add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10,30), LocalTime.of(11,30)));
      add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(13,30), LocalTime.of(14,30)));
      add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14,30), LocalTime.of(15,30)));
    }};
    
    List<Room> roomList = new ArrayList<Room>(){{
      add(new Room("Room A"));
      add(new Room("Room B"));
      add(new Room("Room C"));
    }};

    List<Lesson> lessonList = new ArrayList<Lesson>(){{
      add(new Lesson(101L, "数学", "梅花老师", "3年级"));
      add(new Lesson(102L, "物理", "柯南老师", "3年级"));
      add(new Lesson(103L, "地理", "陂东老师", "3年级"));
      add(new Lesson(104L, "英语", "乔纳森老师", "3年级"));
      add(new Lesson(105L, "西班牙语", "克鲁兹老师", "3年级"));

      add(new Lesson(201L, "数学", "梅花老师", "4年级"));
      add(new Lesson(202L, "化学", "柯南老师", "4年级"));
      add(new Lesson(203L, "历史", "乔纳森老师", "4年级"));
      add(new Lesson(204L, "英语", "克鲁兹老师", "4年级"));
      add(new Lesson(205L, "法语", "柯南老师", "4年级"));
    }};

    return new TimeTable(timeslotList, roomList, lessonList);
  }
}