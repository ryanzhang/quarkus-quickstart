package org.acme.quarkus;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/timeTable")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TimeTableResource {
    private static final Logger log = LoggerFactory.getLogger(TimeTableResource.class.getName());

    @Inject
    SolverManager<TimeTable, UUID> solverManager;

    @POST
    @Path("/solve")
    public TimeTable solve(TimeTable problem){
      UUID problemId = UUID.randomUUID();
      //提交问题
      SolverJob<TimeTable, UUID> solverJob = solverManager.solve(problemId, problem);
      TimeTable solution;
      try {
        solution = solverJob.getFinalBestSolution();
      } catch (InterruptedException |ExecutionException e) {
        throw new IllegalStateException("Solving failed.", e);
      }
      return solution;
    }

}