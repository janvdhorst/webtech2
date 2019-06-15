package de.ls5.wt2;

import java.util.Date;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DBNews extends DBIdentified {

    private Date publishedOn;
    private String headline;
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonbDateFormat(value = JsonbDateFormat.TIME_IN_MILLIS)
    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

