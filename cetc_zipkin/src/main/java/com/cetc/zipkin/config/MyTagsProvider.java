//package com.cetc.zipkin.config;
//
//import io.micrometer.core.instrument.Tag;
//import io.micrometer.core.instrument.Tags;
//import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTags;
//import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class MyTagsProvider implements WebMvcTagsProvider {
//    @Override
//    public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler, Throwable exception) {
//        return Tags.of(WebMvcTags.method(request), WebMvcTags.uri(request, response), WebMvcTags.status(response));
//    }
//
//    @Override
//    public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler) {
//        return Tags.of(WebMvcTags.method(request), WebMvcTags.uri(request, null));
//    }
//}
