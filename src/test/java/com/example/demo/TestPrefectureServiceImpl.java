package com.example.demo;

import com.example.demo.application.prefecture.PrefectureModel;
import com.example.demo.application.prefecture.PrefectureServiceImpl;
import com.example.demo.domain.prefecture.Prefecture;
import com.example.demo.domain.prefecture.PrefectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TestPrefectureServiceImpl {

    @Mock
    private PrefectureRepository prefectureRepository;

    @InjectMocks
    private PrefectureServiceImpl prefectureServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void paramIs1() {

        Prefecture testPrefecture = new Prefecture(1,"Hokkaido",1);
        List<Prefecture> testPrefectureList = new ArrayList<>();
        testPrefectureList.add(testPrefecture);

        when(prefectureRepository.find(1)).thenReturn(testPrefectureList);

        Optional<Integer> testId = Optional.of(1);

        List<PrefectureModel> result = prefectureServiceImpl.getPrefectureList(testId);

        assertAll(
                () -> verify(prefectureRepository, times(1)).find(1),
                () -> assertEquals(1, result.get(0).getId()),
                () -> assertEquals("Hokkaido", result.get(0).getName()),
                () -> assertEquals(1, result.get(0).getRegion_Id())
        );

    }

}
