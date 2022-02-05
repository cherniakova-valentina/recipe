package src.recipe.recipedemo.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import src.recipe.recipedemo.commands.UnitOfMeasureCommand;
import src.recipe.recipedemo.converters.UnitOfMeasureToUnitOfMeasureCommand;
import src.recipe.recipedemo.domain.UnitOfMeasure;
import src.recipe.recipedemo.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

    UnitOfMeasureService unitOfMeasureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void listAllUoms() {
        // given
        Set<UnitOfMeasure> uoms = new HashSet<>();

        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1l);
        uoms.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom1.setId(2l);
        uoms.add(uom2);

        // when
        when(unitOfMeasureRepository.findAll()).thenReturn(uoms);
        Set<UnitOfMeasureCommand> commands = unitOfMeasureService.listAllUoms();

        // then
        assertEquals(2, commands.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}