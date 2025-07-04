package com.pskwiercz.springaifeatures.model;

import java.util.List;

public record Movie(String title, List<String> actors, String director) {
}
