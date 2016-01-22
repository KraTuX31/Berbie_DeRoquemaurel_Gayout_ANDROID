package com.m2dl.maf.makeafocal;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;
import android.test.suitebuilder.annotation.SmallTest;

import com.m2dl.maf.makeafocal.database.Database;
import com.m2dl.maf.makeafocal.model.User;

public class TestDatabaseUser extends AndroidTestCase {
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

    @SmallTest
    public void testDbAddUser(){
        User u = new User("toto");
        db.createUser(u);
        String s =context.getDatabasePath("makeafocal.db").toString();
        u = db.getUser(u.getId());
        assert(u != null);
        assertEquals(u.getUserName(), "toto");
    }

    @SmallTest
    public void testDbModelAddUser() {
        User u = new User("toto");
        u.create(context);
    }
}