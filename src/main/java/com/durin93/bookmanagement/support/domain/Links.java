package com.durin93.bookmanagement.support.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class Links extends ResourceSupport {
    @JsonIgnoreProperties({"media", "hreflang", "title", "type", "deprecation"})
    private List<Link> links = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    @Override
    public Link getLink(String rel) {
        return links.stream().filter(l -> l.getRel().equals(rel)).findFirst().orElse(null);
    }

    public void add(Link link) {
        links.add(link);
    }

    @Override
    public String toString() {
        return "links{" +
                getLinks() +
                '}';
    }
}
