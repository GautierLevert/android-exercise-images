package fr.iut_amiens.imagelist.model;

import org.joda.time.Instant;

import java.io.Serializable;
import java.net.URL;

public final class Image implements Serializable {

    private long id;

    private Instant date;

    private String title;

    private String description;

    private URL thumbnailUrl;

    private URL fullUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(URL thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public URL getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(URL fullUrl) {
        this.fullUrl = fullUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return id == image.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", thumbnailUrl=" + thumbnailUrl +
                ", fullUrl=" + fullUrl +
                '}';
    }
}
