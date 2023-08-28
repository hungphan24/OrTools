package com.example.OrTools;
import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.FirstSolutionStrategy;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.google.ortools.constraintsolver.RoutingSearchParameters;
import com.google.ortools.constraintsolver.main;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class OrToolsService {
    private static final Logger logger = Logger.getLogger(OrToolsService.class.getName());

    /// @brief Print the solution.
    static orToolsResponse returnSolution(
            RoutingModel routing, RoutingIndexManager manager, Assignment solution) {

        orToolsResponse response = new orToolsResponse();

        // Solution cost.
        logger.info("Objective: " + solution.objectiveValue() + "miles");
        response.setObjective(solution.objectiveValue());

        // Inspect solution.
        logger.info("Route:");
        long routeDistance = 0;
        String route = "";
        long index = routing.start(0);
        while (!routing.isEnd(index)) {
            route += manager.indexToNode(index) + " -> ";
            long previousIndex = index;
            index = solution.value(routing.nextVar(index));
            routeDistance += routing.getArcCostForVehicle(previousIndex, index, 0);
        }
        route += manager.indexToNode(routing.end(0));
        logger.info(route);
        response.setPath(route);

        logger.info("Route distance: " + routeDistance + "miles");
        response.setRouteDistance(routeDistance);
        return response;
    }

    public orToolsResponse handleOrTools(OrToolsDto orToolsDto) throws Exception {

        Loader.loadNativeLibraries();
        // Instantiate the data problem.
        //final DataModel data = new DataModel();

        // Create Routing Index Manager
        RoutingIndexManager manager =
                new RoutingIndexManager(orToolsDto.getDistanceMatrix().length, orToolsDto.getVehicleNumber(), orToolsDto.getDepot());

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);

        // Create and register a transit callback.
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return orToolsDto.getDistanceMatrix()[fromNode][toNode];
                });

        // Define cost of each arc.
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // Setting first solution heuristic.
        RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                        .build();

        // Solve the problem.
        Assignment solution = routing.solveWithParameters(searchParameters);

        // Print solution on console.
        //printSolution(routing, manager, solution);
        orToolsResponse orToolsResponse = returnSolution(routing, manager, solution);
        return orToolsResponse;

    }
}
