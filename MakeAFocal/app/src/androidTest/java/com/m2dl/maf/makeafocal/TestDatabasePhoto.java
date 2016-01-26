package com.m2dl.maf.makeafocal;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Pair;

import com.m2dl.maf.makeafocal.database.Database;
import com.m2dl.maf.makeafocal.model.Photo;
import com.m2dl.maf.makeafocal.model.User;

public class TestDatabasePhoto extends AndroidTestCase {
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
    public void testDbAddPhoto(){
        User u = new User("toto");
        u.create(context);
        Photo p = new Photo("/superpath",
                new Pair<>(0.1f, 0.2f), u
        );

        db.insertPhoto(p);
        Photo p2 = db.getPhoto(p.getId());
        assert(p2 != null);
        assertEquals(p.getPath(), p2.getPath());
        assertEquals(p.getUser().getUserName(), p2.getUser().getUserName());

    }

    @SmallTest
    public void testDbModelAddUser() {
        Photo p = new Photo("/superpath",
                new Pair<>(0.1f, 0.2f), new User("toto")
        );
        p.create(context);
    }
}