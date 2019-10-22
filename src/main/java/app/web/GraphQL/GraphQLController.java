package app.web.GraphQL;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionInput;
import graphql.GraphQL;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anton_arakcheyev on 29/11/2018.
 */
@RestController
@SuppressWarnings("HardcodedFileSeparator")
public class GraphQLController {
    private GraphQL graphQL;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    @Named("GraphQL")
    public void setGraphQL(final GraphQL graphQL) {
        this.graphQL = graphQL;
    }

    @RequestMapping(value = "/graphql", method = RequestMethod.POST)
    public Object graphql(@RequestBody final String input) throws IOException {
        try {
            System.out.println("\r\n ************ GraphQL input ****************");
            System.out.println("\r\n >>>>>>>> GraphQL Controller input: " + input);
        } catch (Exception e) {
            System.out.println("Exception while retrieving input " + e.getMessage());
        }

        final GQLQuery gqlQuery = objectMapper.readValue(input, GQLQuery.class);
        //final RuntimeContext context = newRuntimeContext(identity);
        try {
            System.out.println("\r\n ************ variables ****************");
            System.out.println(gqlQuery.variables);
        } catch (Exception e) {
            System.out.println("Exception while retrieving variables " + e.getMessage());
        }
        System.out.println("\r\n ************ GraphQL END ****************");
        final ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(gqlQuery.getQuery())

                .variables(gqlQuery.getVariables())
                .operationName(gqlQuery.getOperationName())

                //.context(context)
                .build();
        if (gqlQuery.getQuery().contains("IntrospectionQuery")) {
            final Map<String, Object> result = new HashMap<>();
            result.put("data", graphQL.execute(executionInput).getData());
            return result;
        } else {
            return graphQL.execute(executionInput).toSpecification();

        }
    }

    private static class GQLQuery {
        private String operationName;
        private String query;
        private Map<String, Object> variables;

        public String getOperationName() {
            return operationName;
        }

        public void setOperationName(String operationName) {
            this.operationName = operationName;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public Map<String, Object> getVariables() {
            return variables;
        }

        public void setVariables(Map<String, Object> variables) {
            this.variables = variables;
        }
    }
}
