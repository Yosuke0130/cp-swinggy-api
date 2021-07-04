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
    public void paramIs1() {

        int testPrefectureId = 1;
        String testName = "Hokkaido";
        int testRegionId = 1;

        Prefecture testPrefecture = new Prefecture(testPrefectureId, testName, testRegionId);
        List<Prefecture> testPrefectureList = new ArrayList<>();
        testPrefectureList.add(testPrefecture);

        doReturn(testPrefectureList).when(prefectureRepository).find(testRegionId);

        List<PrefectureModel> result = prefectureServiceImpl.getPrefectureList(Optional.of(testRegionId));

        assertAll(
                () -> assertEquals(testPrefectureId, result.get(0).getPrefectureId()),
                () -> assertEquals(testName, result.get(0).getName()),
                () -> assertEquals(testRegionId, result.get(0).getRegionId()),
                () -> assertEquals(1, result.size())
        );
    }

    @Test
    public void paramIsNull() {

        int testPrefectureId = 1;
        String testName = "Hokkaido";
        int testRegionId = 1;

        Prefecture testPrefecture = new Prefecture(testPrefectureId, testName, testRegionId);
        List<Prefecture> testPrefectureList = new ArrayList<>();
        testPrefectureList.add(testPrefecture);

        Optional<Integer> nullRegionId = Optional.empty();

        doReturn(testPrefectureList).when(prefectureRepository).findAll();

        List<PrefectureModel> result = prefectureServiceImpl.getPrefectureList(nullRegionId);

        assertAll(
                () -> assertEquals(testPrefectureId, result.get(0).getPrefectureId()),
                () -> assertEquals(testName, result.get(0).getName()),
                () -> assertEquals(testRegionId, result.get(0).getRegionId()),
                () -> assertEquals(1, result.size())
        );
    }
}
