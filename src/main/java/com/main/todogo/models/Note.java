package com.main.todogo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noteId;

    @NotBlank(message = "Please enter text")
    private String title;

    @Lob
    @NotBlank(message = "Please enter text")
    private String text;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    @ManyToOne
    private User user;

    public String getShortTitle() {
        if (this.title.length() > 11) {
            return this.title.substring(0, 11).concat("...");
        }

        return this.title;
    }

    public String getShortText() {
        text = text.replaceAll("<.*?>", "");
        if (text.length() > 276) {
            return text.substring(0, 276).concat("...");
        }

        return this.text;
    }
}
