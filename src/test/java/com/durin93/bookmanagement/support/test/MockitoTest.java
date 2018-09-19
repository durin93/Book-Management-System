package com.durin93.bookmanagement.support.test;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class MockitoTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

}
