package com.main.todogo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
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
