package platform;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Configuration
public class BeanConfiguration {

    @Bean
    public HttpHeaders getHeader() {
        return new HttpHeaders();
    }

    @Bean
    public Code getCode() {
        return new Code("public static void main(String[] args) {\n" +
                "    SpringApplication.run(CodeSharingPlatform.class, args);");
    }

    @Bean
    public LinkedHashMap<Integer, Code> getCodeLinkedHashMap() {
        return new LinkedHashMap<Integer, Code>();
    }

}
