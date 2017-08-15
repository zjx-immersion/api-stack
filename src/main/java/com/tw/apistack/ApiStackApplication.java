package com.tw.apistack;

import com.tw.apistack.config.Constants;
import com.tw.apistack.core.todo.model.Todo;
import com.tw.apistack.core.todo.DummyTodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;


@SpringBootApplication
public class ApiStackApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ApiStackApplication.class);

    @Autowired
    private Environment env;

    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT)
                && activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
            LOG.error("You have misconfigured your application! It should not run "
                    + "with both the 'dev' and 'prod' profiles at the same time.");
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(ApiStackApplication.class);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        LOG.info("\n----------------------------------------------------------\n\t"
                        + "Application '{}' is running! Access URLs:\n\t"
                        + "Local: \t\t{}://localhost:{}\n\t"
                        + "External: \t{}://{}:{}\n\t"
                        + "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                env.getProperty("server.port"),
                protocol,
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getActiveProfiles());
    }


    @Bean
    @Profile(Constants.SPRING_PROFILE_LOCAL)
    public CommandLineRunner setup(DummyTodoRepository toDoRepository) {
        return (args) -> {
            toDoRepository.add(new Todo("Remove unused imports", true));
            toDoRepository.add(new Todo("Clean the code", true));
            toDoRepository.add(new Todo("Build the artifacts", false));
            toDoRepository.add(new Todo("Deploy the jar file", true));
            LOG.info("The sample data has been generated");
        };
    }

    @Bean
    @Profile(Constants.SPRING_PROFILE_TEST)
    public CommandLineRunner setupTestEnv(DummyTodoRepository toDoRepository) {
        return (args) -> {
            toDoRepository.add(new Todo("test-A", false));
            toDoRepository.add(new Todo("test-B", false));
            LOG.info("The test data has been generated");
        };
    }
}
