package com.project.app.daos.position;

import com.project.app.coredb.AbstractGenericDaoTestCase;
import com.project.app.dtos.instrument.InstrumentDto;
import com.project.app.dtos.position.PositionDto;
import com.project.app.exceptions.CannotPersistEntityException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import static java.util.stream.Collectors.toList;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author p.rafailov
 */
public class PositionDaoTestCase extends AbstractGenericDaoTestCase<PositionDto> {

    private DefaultPositionDao posDao;

    @Before
    public final void setUp() {
        posDao = new DefaultPositionDao();
    }

    @After
    public void tearDown() {
        posDao = null;
    }

    @Test
    public void savePositionTest() {
        Long id = getRandomId();
        PositionDto dto = posDao.loadById(id);
        dto.setId(null);
        posDao.save(dto);
        LOGGER.log(Level.INFO, "Saved record: {0}", dto.toString());
        assertNotNull(dto.getId());
        assertNotNull(dto.getInstrument());
        assertNotNull(dto.getInstrument().getId());
    }

    @Test(expected = CannotPersistEntityException.class)
    public void saveWithNullReferenceTest() {
        Timestamp t1 = Timestamp.valueOf(LocalDateTime.now());
        PositionDto dto = new PositionDto(t1, "payer", "receiver", 0, 0, null);
        posDao.save(dto);

    }

    @Test(expected = CannotPersistEntityException.class)
    public void saveWithNoFKTest() {
        // non-existant instrument in db!
        InstrumentDto inst = new InstrumentDto("ddddddd", 23233,
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                "somedata", "test1", "credit");
        Timestamp t1 = Timestamp.valueOf(LocalDateTime.now());
        PositionDto dto = new PositionDto(t1, "payer", "receiver", 0, 3, inst);
        posDao.save(dto);
    }

    @Test
    public void loadByIdTest() {
        Long id = getRandomId();
        PositionDto dto = posDao.loadById(id);
        LOGGER.log(Level.ALL, "Loaded record: {0}", dto.toString());
        assertNotNull(dto);
        assertNotNull(dto.getId());
        assertNotNull(dto.getInstrument());
        assertNotNull(dto.getInstrument().getId());
    }

    @Test
    public void loadAllTest() {
        List<PositionDto> dtoList = posDao.loadAll();
        dtoList.stream().forEach(dto -> System.out.println(dto.getInstrument().getId()));
        System.out.println("Loaded records count: " + dtoList.size());
        assertNotNull(dtoList);
    }

    @Test
    public void deleteTest() {
        Long id = getRandomId();
        PositionDto pos = posDao.loadById(id);
        posDao.delete(pos);
        LOGGER.log(Level.INFO, "Deleted record: {0}", pos.toString());
        assertNull(pos.getId());
    }

    @Test
    public void updatePositionTest() {
        Long id = getRandomId();
        PositionDto dto = posDao.loadById(id);
        dto.setPayer("dsdsd");
        dto.setPrincipal(213);
        posDao.update(dto);
        PositionDto updatedPos = posDao.loadById(id);
        LOGGER.log(Level.INFO, "Updated record: {0}", updatedPos.toString());
        assertEquals(updatedPos.getPayer(), dto.getPayer());
    }

    @Test
    public void loadAllPositionsByReferenceTest() {
        Long fk = posDao.loadById(getRandomId()).getInstrument().getId();
        List<PositionDto> dtos = posDao.loadAllByReference(fk);
        boolean actual = dtos.stream().allMatch(pos -> pos.getInstrument().getId().equals(fk));
        assertTrue(actual);
    }

    @Test
    public void loadAllByTypeOfReferenceTest() {
        InstrumentDto ins = posDao.loadById(getRandomId()).getInstrument();
        List<PositionDto> dtos = posDao.loadAllByTypeOfReference(ins);
        assertNotNull(dtos);

        boolean actual = dtos.stream().allMatch(dto -> dto.getInstrument().getIntrumentType().equalsIgnoreCase(ins.getIntrumentType()));
        assertTrue(actual);
        // select * from positions where (select id from instruments where instrument_type='credit') >=0
    }

    @Override
    protected List<PositionDto> getRecords() {
        return posDao.loadAll();
    }

    @Override
    protected Long getDtoId(PositionDto dto) {
        return dto.getId();
    }

}
