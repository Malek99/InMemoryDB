package DB.Commands;

import DB.Conditions.Condition;
import DB.Conditions.IDEqualCondition;
import DB.DatabaseKey;
import DB.Record;
import DB.RecordKeysCollection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class ConditionalCommand extends Command {
    protected final Condition condition;

    public ConditionalCommand(Condition condition) {
        this.condition = condition;
    }

    public boolean isOnSingleRecord() {
        return condition instanceof IDEqualCondition;
    }

    @Override
    public String execute() {
        return executeOnListOfRecords(getAllRecordsSatisfiedByCondition());
    }

    protected abstract String executeOnListOfRecords(List<Record> recordList);

    protected List<Record> getAllRecordsSatisfiedByCondition() {
        List<Record> satisfied = new ArrayList<>();
        // If the condition is IDEqualCondition return a list with that single record
        if(isOnSingleRecord()){
            DatabaseKey recordKey = ((IDEqualCondition) condition).getKey();
            Record record = storage.read(recordKey);
            satisfied.add(record);
            return satisfied;
        }
        RecordKeysCollection allKeys = storage.getKeysCollection();
        for (DatabaseKey recordKey : allKeys) {
            Record record = storage.read(recordKey);
            if(condition.isSatisfiedOnRecord(record))
                satisfied.add(record);
        }
        return satisfied;
    }
}
