package com.example.demo.prefecture;

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

        int prefectureId = 1;
        String name = "Hokkaido";
        int regionId = 1;

        Prefecture prefecture = new Prefecture(prefectureId, name, regionId);
        List<Prefecture> prefectureList = new ArrayList<>();
        prefectureList.add(prefecture);

        when(prefectureRepository.find(regionId)).thenReturn(prefectureList);

        List<PrefectureModel> result = prefectureServiceImpl.getPrefectureList(Optional.of(regionId));

        assertAll(
                () -> verify(prefectureRepository, times(1)).find(regionId),
                () -> assertEquals(prefectureId, result.get(0).getId()),
                () -> assertEquals(name, result.get(0).getName()),
                () -> assertEquals(regionId, result.get(0).getRegion_Id())
        );

    }

}
