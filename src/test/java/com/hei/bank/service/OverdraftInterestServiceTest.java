package com.hei.bank.service;

import com.hei.bank.DAO.OverdraftInterestDAO;
import com.hei.bank.model.OverdraftInterest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OverdraftInterestServiceTest {

    @Mock
    private OverdraftInterestDAO overdraftInterestDAO;

    @InjectMocks
    private OverdraftInterestService overdraftInterestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() throws SQLException {
        // Given
        List<OverdraftInterest> expectedOverdraftInterests = new ArrayList<>();
        // Add some sample overdraft interests to the list
        expectedOverdraftInterests.add(new OverdraftInterest(UUID.randomUUID(), 0.05, 0.07, null));
        expectedOverdraftInterests.add(new OverdraftInterest(UUID.randomUUID(), 0.06, 0.08, null));

        // Mock the behavior of overdraftInterestDAO.findAll() to return the expected list of overdraft interests
        when(overdraftInterestDAO.findAll()).thenReturn(expectedOverdraftInterests);

        // When
        List<OverdraftInterest> actualOverdraftInterests = overdraftInterestService.findAll();

        // Then
        assertEquals(expectedOverdraftInterests.size(), actualOverdraftInterests.size());
        for (int i = 0; i < expectedOverdraftInterests.size(); i++) {
            assertEquals(expectedOverdraftInterests.get(i), actualOverdraftInterests.get(i));
        }
    }

    @Test
    void testFindById() throws SQLException {
        // Given
        UUID overdraftInterestId = UUID.randomUUID();
        OverdraftInterest expectedOverdraftInterest = new OverdraftInterest(overdraftInterestId, 0.05, 0.07, null);

        // Mock the behavior of overdraftInterestDAO.findById() to return the expected overdraft interest
        when(overdraftInterestDAO.findById(overdraftInterestId)).thenReturn(expectedOverdraftInterest);

        // When
        OverdraftInterest actualOverdraftInterest = overdraftInterestService.findById(overdraftInterestId);

        // Then
        assertEquals(expectedOverdraftInterest, actualOverdraftInterest);
    }

    @Test
    void testSave() throws SQLException {
        // Given
        OverdraftInterest overdraftInterestToSave = new OverdraftInterest(UUID.randomUUID(), 0.05, 0.07, null);

        // Mock the behavior of overdraftInterestDAO.save() to return the saved overdraft interest
        when(overdraftInterestDAO.save(overdraftInterestToSave)).thenReturn(overdraftInterestToSave);

        // When
        OverdraftInterest savedOverdraftInterest = overdraftInterestService.save(overdraftInterestToSave);

        // Then
        assertEquals(overdraftInterestToSave, savedOverdraftInterest);
    }
}