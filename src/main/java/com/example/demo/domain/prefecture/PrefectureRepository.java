package com.example.demo.domain.prefecture;

import java.util.List;
import java.util.Optional;

public interface PrefectureRepository {
    List<Prefecture> find(Optional<String> regionId);
}
