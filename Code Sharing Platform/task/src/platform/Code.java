package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "CodeSnippets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @NotNull
    @JsonIgnore
    private UUID codeId;
    @Column
    private String code;
    @Column
    private String date;
    @Column
    @JsonIgnore
    private boolean isRestricted;
    @Column
    @JsonIgnore
    private boolean isTimeRestricted;
    @Column
    @JsonIgnore
    private boolean isViewRestricted;
    @Column
    @JsonIgnore
    private boolean isDepleted;
    @Column
    private Long time;
    @Column
    private Integer views;
    @Column
    @JsonIgnore
    private Long originalTime;

    public Code(String code) {
        this.code = code;
        this.isViewRestricted = false;
        this.isTimeRestricted = false;
        this.isDepleted = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
        this.date = LocalDateTime.now().format(formatter);
    }
}
