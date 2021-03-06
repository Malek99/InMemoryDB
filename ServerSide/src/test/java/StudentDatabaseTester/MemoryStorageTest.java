package StudentDatabaseTester;

import DB.Record;
import DB.Storages.RecordNotFoundException;
import DB.Storages.MemoryStorage;
import DB.Attributes.StudentID;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MemoryStorageTest extends DatabaseStorageTest {
    private static int cacheSize = 100;

    @BeforeClass
    public static void setStorage() {
        storage = new MemoryStorage(cacheSize);
    }

    @Test(expected = RecordNotFoundException.class)
    public void testCacheMissForNonExistentRecord() throws RecordNotFoundException {
        Record record = new Record(new StudentID(1) );
        storage.read(record.getKey());
    }

    @Test(expected = RecordNotFoundException.class)
    public void testCacheMissForRecordsWithSameCacheKey() throws RecordNotFoundException {
        int id = 1;
        Record record = new Record(new StudentID(id));
        Record record2 = new Record(new StudentID(cacheSize + id));
        storage.write(record);
        storage.write(record2);
        assertFalse(storage.containsKey(record.getKey()));
        assertTrue(storage.containsKey(record2.getKey()));
        storage.read(record.getKey());
    }
}
