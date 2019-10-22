package app.web;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.coxautodev.graphql.tools.SchemaParser;
import app.web.GraphQL.Scalars;
import app.core.Utils.MigrationUtil;
import graphql.GraphQL;
import graphql.execution.SubscriptionExecutionStrategy;
import graphql.execution.instrumentation.ChainedInstrumentation;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import graphql.schema.GraphQLSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collections;

/**
 * Created by anton_arakcheyev on 13/11/2018.
 */
@Configuration
@EnableWebMvc
@ComponentScan("app.web")
public class WebConfig extends WebMvcConfigurerAdapter {

    /*private List<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers = new ArrayList<>();*/


    public WebConfig() {
        System.out.println(this.getClass().getName() + "construction");
    }

    /*@Inject
    public void setHandlerMethodArgumentResolvers(List<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers) {
        this.handlerMethodArgumentResolvers.addAll(handlerMethodArgumentResolvers);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.addAll(handlerMethodArgumentResolvers);
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Bean
    public GraphQLSchema graphQLSchema(final GraphQLResolver[] resolvers) {
        /*System.out.println(" ********************************************* ");
        List<File> folders = new ArrayList<>();
        File f = new File("src/main/resources/");
        folders.add(f);
        f = new File("target/classes/resources/");
        folders.add(f);
        for(File folder:folders) {
            File[] listOfFiles = folder.listFiles();
            System.out.println(" *********************** ["+folder.getName()+"]********************** ");
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("File " + listOfFiles[i].getName());
                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                }
            }
        }

        System.out.println(" ********************************************* ");*/
        return SchemaParser.newParser()
                .file("../classes/resources/schema.graphqls")
                .resolvers(resolvers)
                .scalars(Scalars.DateTime)
                .build()
                .makeExecutableSchema();
    }

    @Bean("GraphQL")
    public GraphQL graphQL(final GraphQLSchema schema) {
        final Instrumentation instrumentation = new ChainedInstrumentation(
                Collections.<Instrumentation>singletonList(new TracingInstrumentation())
        );

        return GraphQL.newGraphQL(schema)
                .subscriptionExecutionStrategy(new SubscriptionExecutionStrategy())
                .instrumentation(instrumentation).build();

    }

    @Bean("MigrationUtils")
    public MigrationUtil createUtils(){
        return new MigrationUtil();
    }

}
