package org.acme.quarkus;

import org.acme.quarkus.domain.Lesson;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class TimeTableConstraintProvider implements ConstraintProvider {

  @Override
  public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
    return new Constraint[]{
      //Hard constraints
      roomConflict(constraintFactory),
      teacherConflict(constraintFactory),
      studentGroupConflict(constraintFactory),
    };
  }

  private Constraint roomConflict(ConstraintFactory constraintFactory){
    //一个教室只能上一堂课
    return constraintFactory.from(Lesson.class)
              .join(Lesson.class, 
                  Joiners.equal(Lesson::getTimeslot),
                  Joiners.equal(Lesson::getRoom),
                  Joiners.lessThan(Lesson::getId)
              ).penalize("Room conflict", HardSoftScore.ONE_HARD);
  }
  private Constraint teacherConflict(ConstraintFactory constraintFactory){
    return constraintFactory
            .fromUniquePair(Lesson.class,
              Joiners.equal(Lesson::getTimeslot),
              Joiners.equal(Lesson::getTeacher))
            .penalize("Teacher conflict", HardSoftScore.ONE_HARD);
  }
  private Constraint studentGroupConflict(ConstraintFactory constraintFactory){
    return constraintFactory
            .fromUniquePair(Lesson.class, 
              Joiners.equal(Lesson::getTimeslot),
              Joiners.equal(Lesson::getStudentGroup))
            .penalize("Student group conflict", HardSoftScore.ONE_HARD);
  }

}