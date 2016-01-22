package com.m2dl.maf.makeafocal;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;
import android.test.suitebuilder.annotation.SmallTest;

import com.m2dl.maf.makeafocal.database.Database;
import com.m2dl.maf.makeafocal.model.User;

public class TestDatabase extends AndroidTestCase {
    private Database db;
    RenamingDelegatingContext context;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        context = new RenamingDelegatingContext(getContext(), "test_");
        db = Database.instance(context);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    //According to Zainodis annotation only for legacy and not valid with gradle>1.1:
    @SmallTest
    public void testAddUser(){
        db.createUser(new User(context, "toto"));
    }
}