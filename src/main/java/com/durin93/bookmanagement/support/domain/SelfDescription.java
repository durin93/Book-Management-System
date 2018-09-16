package com.durin93.bookmanagement.support.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class SelfDescription extends ResourceSupport {
    @JsonIgnoreProperties({"media","hreflang","title","type","deprecation"})
    private List<Link> links = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public Link getLink(String rel) {
        for (Link link : links) {
            if (link.getRel().equals(rel)) {
                return link;
            }
        }
        return null;
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
