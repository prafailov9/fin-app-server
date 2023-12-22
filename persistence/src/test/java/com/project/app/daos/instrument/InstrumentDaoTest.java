package com.project.app.daos.instrument;

import com.project.app.converters.entityconverters.instrumentconverters.InstrumentConverter;
import com.project.app.converters.entityconverters.instrumentconverters.InstrumentConverterFactory;
import com.project.app.coredb.AbstractGenericDaoTest;
import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.entities.instrument.Instrument;
import com.project.app.entities.instrument.Share;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Plamen
 */
public class InstrumentDaoTest extends AbstractGenericDaoTest<InstrumentDto> {

    private DefaultInstrumentDao insDao;

    @Before
    public void setUp() {
        insDao = new DefaultInstrumentDao();
    }

    @After
    public void tearDown() {
        insDao = null;
    }

    @Test
    public void saveDtoTest() {
        LocalDateTime ldt = LocalDateTime.of(2019, Month.MARCH, 1, 12, 1);
        Timestamp t1 = Timestamp.valueOf(LocalDateTime.now());
        Timestamp t2 = Timestamp.valueOf(ldt);
        InstrumentDto inst = new InstrumentDto("names", 42069, t1, t2,
                "WEEKLY", "ANNUALLY", "credit");
        insDao.save(inst);
        LOGGER.log(Level.INFO, "Dto after save: {0}", inst.toString());
        assertNotNull(inst.getId());
    }

    @Test
    public void convertAndSaveTest() {
        Instrument entity = new Share(null, "shareInst", LocalDateTime.now(), LocalDateTime.now());
        InstrumentConverter<Instrument> conv
                = new InstrumentConverterFactory().getConverter(entity.getType());
        InstrumentDto dto = conv.convertToDto(entity);
        insDao.save(dto);
        assertNotNull(dto.getId());
    }

    @Test
    public void loadByIdTest() {
        Long id = getRandomId();
        InstrumentDto dto = insDao.loadById(id);
        LOGGER.log(Level.INFO, "Loaded Dto : {0}", dto);
        boolean actual = Objects.nonNull(dto);
        assertTrue(actual);
    }

    @Test
    public void loadAllTest() {
        List<InstrumentDto> dtos = insDao.loadAll();
        LOGGER.log(Level.INFO, "Loaded row count: {0}", dtos.size());
        LOGGER.log(Level.INFO, "Dto records count: {0}, Contents: {1}", new Object[]{dtos.size(), dtos});
        assertNotNull(dtos);
    }

    @Test
    public void deleteInstrumentTest() {
        Long randomId = getRandomId();
        InstrumentDto inst = insDao.loadById(randomId);
        insDao.delete(inst);
        LOGGER.log(Level.INFO, "Deleted record: {0}", inst.toString());
        assertNull(inst.getId());
    }

    @Test
    public void updateTest() {
        Long id = getRandomId();
        InstrumentDto dto = insDao.loadById(id);
        String expected = "ddadasdasdasdasdasd";
        dto.setInstrumentName(expected);
        dto.setInterestRate(231312321);
        insDao.update(dto);
        InstrumentDto updatedIns = insDao.loadById(id);
        String actual = updatedIns.getInstrumentName();
        LOGGER.log(Level.INFO, "Updated record: {0}", updatedIns.toString());
        assertEquals(expected, actual);
    }

    @Test
    public void loadAllByTypeTest() {
        String type = "credit";
        List<InstrumentDto> credits = insDao.loadAllByType(type);

        assertNotNull(credits);
        boolean actual = credits.stream().allMatch(dto -> dto.getIntrumentType().equalsIgnoreCase(type));
        assertTrue(actual);

    }

    @Override
    protected List<InstrumentDto> getRecords() {
        return insDao.loadAll();
    }

    @Override
    protected Long getDtoId(InstrumentDto dto) {
        return dto.getId();
    }

}
