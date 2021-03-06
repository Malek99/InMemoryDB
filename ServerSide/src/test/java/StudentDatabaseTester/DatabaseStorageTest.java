package StudentDatabaseTester;

import DB.Record;
import DB.Storages.DatabaseStorage;
import DB.Storages.RecordNotFoundException;
import DB.Attributes.StudentID;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class DatabaseStorageTest {
    protected static DatabaseStorage storage;

    @Before
    public void deletePreviousRecords(){
        storage.deleteAllRecords();
    }

    @Test
    public void testWriteAndRead() throws RecordNotFoundException {
        Record record = getNewRecordWithID(1);
        record.setAttributeFromNameAndStrValue("name", "malek");
        record.setAttributeFromNameAndStrValue("faculty", "Engineering");
        assertFalse(storage.containsKey(record.getKey()));
        storage.write(record);
        assertTrue(storage.containsKey(record.getKey()));
        Record record1 = storage.read(record.getKey());
        assertEquals(record, record1);
    }

    @Test
    public void testDeletion() throws RecordNotFoundException{
        Record record = getNewRecordWithID(1);
        storage.write(record);
        storage.delete(record.getKey());
        assertFalse(storage.containsKey(record.getKey()));
    }

    protected Record getNewRecordWithID(int ID){
        return new Record(new StudentID(ID));
    }

}
